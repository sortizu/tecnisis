package com.example.tecnisis.ui.artistic_request_evaluation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tecnisis.config.datastore.DataStoreManager
import com.example.tecnisis.config.retrofit.TecnisisApi
import com.example.tecnisis.data.document.DocumentRequest
import com.example.tecnisis.data.evaluations.ArtisticEvaluationRequest
import com.example.tecnisis.data.request.RequestResponse
import com.example.tecnisis.ui.components.convertMillisToDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.json.JSONObject

data class ArtisticRequestEvaluationUiState(
    val rating: Float = 0.0f,
    val result: String = "",
    val request: RequestResponse? = null,
    val reviewDocument: String = "",
    val evaluationSaved: Boolean = false,
    val date: String = ""
)

class ArtisticRequestEvaluationViewModel(idRequest: Long, dataStoreManager: DataStoreManager): ViewModel() {
    private val _uiState = MutableStateFlow(ArtisticRequestEvaluationUiState())
    val uiState: StateFlow<ArtisticRequestEvaluationUiState> = _uiState.asStateFlow()
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message
    private val dataStoreManager: DataStoreManager = dataStoreManager

    init {
        getRequest(idRequest)
        updateDate(System.currentTimeMillis())
    }

    fun updateRating(newRating: Float) {
        _uiState.value = _uiState.value.copy(rating = newRating)
    }
    fun updateReviewDocument(newReviewDocument: String) {
        _uiState.value = _uiState.value.copy(reviewDocument = newReviewDocument)
    }
    fun updateDate(newDateInMillis: Long) {
        _uiState.value = _uiState.value.copy(date = convertMillisToDate(newDateInMillis))
    }
    fun updateMessage(newMessage: String) {
        _message.value = newMessage
    }
    fun updateResult(newResult: String) {
        _uiState.value = _uiState.value.copy(result = newResult)
    }

    fun updateRequest(newRequest: RequestResponse) {
        _uiState.value = _uiState.value.copy(request = newRequest)
    }

    fun updateEvaluationSaved(newEvaluationSaved: Boolean) {
        _uiState.value = _uiState.value.copy(evaluationSaved = newEvaluationSaved)
    }

    fun getRequest(id: Long) {
        viewModelScope.launch {
            try {
                var token = ""
                dataStoreManager.token.let {
                    token = it.first()!!
                }
                val response = TecnisisApi.requestService.getRequest(token, id)
                if (response.isSuccessful) {
                    response.body()?.let {
                        updateRequest(it[0])
                    }
                } else {
                    _message.value = response.message()
                    return@launch
                }
            }
            catch (e: Exception) {
                _message.value = e.message
            }
        }
    }

    fun saveReview() {
        viewModelScope.launch {
            try{
                var date = _uiState.value.date
                var rating = _uiState.value.rating
                var result = _uiState.value.result
                var reviewDocument = _uiState.value.reviewDocument
                if (date.isEmpty() || rating == 0.0f || result.isEmpty() || reviewDocument.isEmpty()){
                    updateMessage("Por favor, completa todos los campos")
                    return@launch
                }
                var idRole = -1L
                dataStoreManager.idRole.let {
                    idRole = it.first()!!.toLong()
                }
                var token = ""
                dataStoreManager.token.let {
                    token = it.first()!!
                }
                //
                var idEvaluation = -1L
                val evaluationResponse = TecnisisApi.evaluationService.getArtisticEvaluationByRequest(token, _uiState.value.request!!.id)
                if (evaluationResponse.isSuccessful){
                    evaluationResponse.body()?.let {
                        idEvaluation = it.id
                    }
                }
                //
                var status = ""
                if (result == "Desaprobar"){
                    status = "Rejected"
                    result = "R"
                }
                else {
                    status = "Approved"
                    result = "A"
                }
                // Register document
                var documentId=-1L
                val documentResponse = TecnisisApi.documentService.uploadDocument(
                    "Bearer $token",
                    DocumentRequest(
                        path = reviewDocument
                    )
                )
                if (documentResponse.isSuccessful){
                    documentResponse.body()?.let {
                        documentId = it.id
                    }
                }

                val response = TecnisisApi.evaluationService.updateArtisticEvaluation(
                    "Bearer $token",
                    ArtisticEvaluationRequest(
                        evaluationDate = date,
                        rating = rating.toInt(),
                        result = result,
                        status = status,
                        specialistId = idRole,
                        documentId = documentId
                    ), idEvaluation
                )
                if (response.isSuccessful){
                    response.body()?.let {
                        updateEvaluationSaved(true)
                        updateMessage("Evaluación guardada correctamente")
                    }
                }
                else{
                    val errorBody = JSONObject(response.errorBody()?.string()!!)
                    updateMessage(errorBody.get("details").toString())
                }
            }
            catch(e: Exception){
                _message.value = e.message
            }
        }
    }
}