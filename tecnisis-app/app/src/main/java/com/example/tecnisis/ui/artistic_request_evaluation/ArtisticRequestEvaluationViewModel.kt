package com.example.tecnisis.ui.artistic_request_evaluation

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tecnisis.R
import com.example.tecnisis.config.datastore.DataStoreManager
import com.example.tecnisis.config.retrofit.TecnisisApi
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
    val specialistId: Long = -1L,
    val evaluationSaved: Boolean = false,
    val date: String = ""
)

class ArtisticRequestEvaluationViewModel(idRequest: Long, dataStoreManager: DataStoreManager): ViewModel() {
    private val _uiState = MutableStateFlow(ArtisticRequestEvaluationUiState())
    val uiState: StateFlow<ArtisticRequestEvaluationUiState> = _uiState.asStateFlow()
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    init {
        getRequest(idRequest)
        loadSpecialistId(dataStoreManager)
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

    fun updateSpecialistId(newSpecialistId: Long) {
        _uiState.value = _uiState.value.copy(specialistId = newSpecialistId)
    }

    fun updateEvaluationSaved(newEvaluationSaved: Boolean) {
        _uiState.value = _uiState.value.copy(evaluationSaved = newEvaluationSaved)
    }

    fun getRequest(id: Long) {
        viewModelScope.launch {
            try {
                val response = TecnisisApi.requestService.getRequest(id)
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

    fun loadSpecialistId(dataStoreManager: DataStoreManager) {
        viewModelScope.launch {
            try {
                var id = -1L
                dataStoreManager.id.first()?.let {
                    id = it.toLong()
                }
                if (id == -1L) {
                    return@launch
                }
                val response = TecnisisApi.specialistService.getSpecialist(id)
                if (response.isSuccessful) {
                    response.body()?.let {
                        updateSpecialistId(it.id)
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
                val date = _uiState.value.date
                val rating = _uiState.value.rating
                val result = _uiState.value.result
                val reviewDocument = _uiState.value.reviewDocument
                val specialistId = _uiState.value.specialistId
                if (date.isEmpty() || rating == 0.0f || result.isEmpty() || reviewDocument.isEmpty()){
                    updateMessage("Por favor, completa todos los campos")
                    return@launch
                }
                val response = TecnisisApi.evaluationService.saveArtisticEvaluation(
                    ArtisticEvaluationRequest(
                        evaluationDate = date,
                        rating = rating.toInt(),
                        result = result,
                        specialistId = specialistId,
                        document = reviewDocument
                    )
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