package com.example.tecnisis.ui.artistic_request_evaluation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tecnisis.config.datastore.DataStoreManager
import com.example.tecnisis.config.retrofit.TecnisisApi
import com.example.tecnisis.data.document.DocumentRequest
import com.example.tecnisis.data.evaluations.ArtisticEvaluationRequest
import com.example.tecnisis.data.request.CreateRequest
import com.example.tecnisis.data.request.RequestResponse
import com.example.tecnisis.data.specialist.SpecialistRequest
import com.example.tecnisis.ui.components.convertMillisToDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.json.JSONObject

data class ArtisticRequestEvaluationUiState(
    val rating: Double = 0.0,
    val result: String = "Aprobar",
    val request: RequestResponse? = null,
    val reviewDocument: String = "",
    val evaluationSaved: Boolean = false,
    val date: String = "",
    val isLoading: Boolean = false
)

class ArtisticRequestEvaluationViewModel(idRequest: Long, evaluationStatus: String, dataStoreManager: DataStoreManager): ViewModel() {
    private val _uiState = MutableStateFlow(ArtisticRequestEvaluationUiState())
    val uiState: StateFlow<ArtisticRequestEvaluationUiState> = _uiState.asStateFlow()
    private val _message = MutableLiveData<String>("")
    val message: LiveData<String> = _message
    private val dataStoreManager: DataStoreManager = dataStoreManager

    init {
        if (evaluationStatus != "PENDING"){
            loadEvaluation(idRequest)
        }
        getRequest(idRequest)
        updateDate(System.currentTimeMillis())
    }

    fun updateRating(newRating: Double) {
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
        when (newResult){
            "Aprobar" -> {
                _uiState.value = _uiState.value.copy(result = "APPROVED")
            }
            "Desaprobar" -> {
                _uiState.value = _uiState.value.copy(result = "REJECTED")
            }
            else -> {
                _uiState.value = _uiState.value.copy(result = "APPROVED")
            }
        }
    }

    fun updateRequest(newRequest: RequestResponse) {
        _uiState.value = _uiState.value.copy(request = newRequest)
    }

    fun updateEvaluationSaved(newEvaluationSaved: Boolean) {
        _uiState.value = _uiState.value.copy(evaluationSaved = newEvaluationSaved)
    }

    fun updateLoading(newLoading: Boolean) {
        _uiState.value = _uiState.value.copy(isLoading = newLoading)
    }

    fun loadEvaluation(idRequest: Long) {
        viewModelScope.launch {
            var token = ""
            dataStoreManager.token.let {
                token = it.first()!!
            }
            val response = TecnisisApi.evaluationService.getArtisticEvaluationByRequest(
                "Bearer $token",
                idRequest
            )
            if (response.isSuccessful) {
                response.body()?.let {
                    updateRating(it.rating)
                    _uiState.value = _uiState.value.copy(result = it.result)
                    // format date to dd/MM/yyyy
                    val date = it.evaluationDate.split("-").reversed().joinToString("/")
                    _uiState.value = _uiState.value.copy(date = date)
                }
            }
        }
    }

    fun getRequest(id: Long) {
        viewModelScope.launch {
            try {
                updateLoading(true)
                var token = ""
                dataStoreManager.token.let {
                    token = it.first()!!
                }
                val response = TecnisisApi.requestService.getRequest("Bearer $token", id)
                if (response.isSuccessful) {
                    response.body()?.let {
                        updateRequest(it)
                    }
                } else {
                    updateMessage("Error: ${response.errorBody()}")
                }
                updateLoading(false)
            }
            catch (e: Exception) {
                updateMessage("Error: ${e.message}")
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
                if (date.isEmpty() || rating == 0.0 || result.isEmpty() || reviewDocument.isEmpty()){
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
                val evaluationResponse = TecnisisApi.evaluationService.getArtisticEvaluationByRequest("Bearer $token", _uiState.value.request!!.id)
                if (evaluationResponse.isSuccessful){
                    evaluationResponse.body()?.let {
                        idEvaluation = it.id
                    }
                }
                //
                var status = ""
                if (result == "Desaprobar"){
                    status = "REJECTED"
                    result = "REJECTED"
                }
                else {
                    status = "APPROVED"
                    result = "APPROVED"
                }
                // change date format to yyyy-MM-dd
                date = date.split("/").reversed().joinToString("-")
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
                        rating = rating,
                        result = result,
                        status = status,
                        specialistId = idRole,
                        documentId = documentId
                    ), idEvaluation
                )
                if (!response.isSuccessful){
                    val errorBody = JSONObject(response.errorBody()?.string()!!)
                    updateMessage(errorBody.get("details").toString())
                    return@launch
                }
                // Update request
                val requestResponse = TecnisisApi.requestService.updateRequest(
                    "Bearer $token",
                    CreateRequest(
                        status = when (status) {
                            "APPROVED" -> "PENDING"
                            else -> "REJECTED"
                        },
                        artworkId = _uiState.value.request!!.artWork.id
                    ),
                    _uiState.value.request!!.id
                )
                if (!requestResponse.isSuccessful){
                    val errorBody = JSONObject(response.errorBody()?.string()!!)
                    updateMessage(errorBody.get("details").toString())
                    return@launch
                }
                // update specialist availability
                val specialistResponse = TecnisisApi.specialistService.updateSpecialist(
                    token = "Bearer $token",
                    specialist = SpecialistRequest(
                        idRole,
                        true)
                    ,
                    id = idRole
                )
                if (specialistResponse.isSuccessful) {
                    specialistResponse.body()?.let {
                        updateMessage("Evaluación guardada correctamente")
                        updateEvaluationSaved(true)
                    }
                }
            }
            catch(e: Exception){
                updateMessage("Error: ${e.message}")
            }
        }
    }
}