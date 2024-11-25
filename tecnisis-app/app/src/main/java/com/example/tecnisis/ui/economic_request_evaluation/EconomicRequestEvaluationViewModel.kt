package com.example.tecnisis.ui.economic_request_evaluation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tecnisis.config.datastore.DataStoreManager
import com.example.tecnisis.config.retrofit.TecnisisApi
import com.example.tecnisis.data.evaluations.ArtisticEvaluationRequest
import com.example.tecnisis.data.evaluations.EconomicEvaluationRequest
import com.example.tecnisis.data.request.RequestResponse
import com.example.tecnisis.ui.components.convertMillisToDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class EconomicRequestReviewUiState(
    val salePrice: Double = 0.0,
    val galleryPercentage: Double = 0.0,
    val document: String = "",
    val request: RequestResponse? = null,
    val specialistId: Long = -1L,
    val date: String = "",
)

class EconomicRequestEvaluationViewModel(idRequest: Long, dataStoreManager: DataStoreManager): ViewModel() {

    private val _uiState = MutableStateFlow(EconomicRequestReviewUiState())
    val uiState: StateFlow<EconomicRequestReviewUiState> = _uiState.asStateFlow()
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    init {
        getRequest(idRequest)
        loadSpecialistId(dataStoreManager)
    }

    fun updateSalePrice(newSalePrice: Double) {
        _uiState.value = _uiState.value.copy(salePrice = newSalePrice)
    }
    fun updateGalleryPercentage(newGalleryPercentage: Double) {
        _uiState.value = _uiState.value.copy(galleryPercentage = newGalleryPercentage)
    }
    fun updateDocument(newDocument: String) {
        _uiState.value = _uiState.value.copy(document = newDocument)
    }
    fun updateRequest(newRequest: RequestResponse) {
        _uiState.value = _uiState.value.copy(request = newRequest)
    }
    fun updateDate(newDate: String) {
        _uiState.value = _uiState.value.copy(date = newDate)
    }
    fun updateMessage(newMessage: String) {
        _message.value = newMessage
    }
    fun updateSpecialistId(newSpecialistId: Long) {
        _uiState.value = _uiState.value.copy(specialistId = newSpecialistId)
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
                // Sets the current date as the evaluation date
                val evaluationDate = convertMillisToDate(System.currentTimeMillis())
                val salePrice = _uiState.value.salePrice
                val galleryPercentage = _uiState.value.galleryPercentage
                val specialistId = _uiState.value.specialistId
                val document = _uiState.value.document

                val response = TecnisisApi.evaluationService.saveEconomicEvaluation(
                    EconomicEvaluationRequest(
                        evaluationDate = evaluationDate,
                        salePrice = salePrice,
                        galleryPercentage = galleryPercentage,
                        specialistId = specialistId,
                        document = document
                    )
                )
                if (response.isSuccessful){
                    response.body()?.let {
                        _message.value = it
                    }
                }
                else{
                    _message.value = response.message()
                }
            }
            catch(e: Exception){
                _message.value = e.message
            }
        }
    }

}