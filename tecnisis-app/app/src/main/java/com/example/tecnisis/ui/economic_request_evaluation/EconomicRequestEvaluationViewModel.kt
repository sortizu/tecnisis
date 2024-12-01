package com.example.tecnisis.ui.economic_request_evaluation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tecnisis.config.datastore.DataStoreManager
import com.example.tecnisis.config.retrofit.TecnisisApi
import com.example.tecnisis.data.document.DocumentRequest
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
    val evaluationSaved: Boolean = false,
    val date: String = "",
)

class EconomicRequestEvaluationViewModel(idRequest: Long, dataStoreManager: DataStoreManager): ViewModel() {

    private val _uiState = MutableStateFlow(EconomicRequestReviewUiState())
    val uiState: StateFlow<EconomicRequestReviewUiState> = _uiState.asStateFlow()
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message
    private val dataStoreManager: DataStoreManager = dataStoreManager

    init {
        getRequest(idRequest)
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
                val response = TecnisisApi.requestService.getRequest(token,id)
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
                // Sets the current date as the evaluation date
                val evaluationDate = convertMillisToDate(System.currentTimeMillis())
                val salePrice = _uiState.value.salePrice
                val galleryPercentage = _uiState.value.galleryPercentage
                val document = _uiState.value.document
                if (evaluationDate.isEmpty() || salePrice == 0.0 || galleryPercentage == 0.0 || document.isEmpty()){
                    updateMessage("Por favor, completa todos los campos")
                    return@launch
                }
                var token = ""
                dataStoreManager.token.let {
                    token = it.first()!!
                }

                var idEvaluation = -1L
                val evaluationResponse = TecnisisApi.evaluationService.getArtisticEvaluationByRequest(token, _uiState.value.request!!.id)
                if (evaluationResponse.isSuccessful){
                    evaluationResponse.body()?.let {
                        idEvaluation = it.id
                    }
                }

                var status = "Approved"
                var idRole = -1L
                dataStoreManager.idRole.let {
                    idRole = it.first()!!.toLong()
                }
                // Register document
                var documentId=-1L
                val documentResponse = TecnisisApi.documentService.uploadDocument(
                    "Bearer $token",
                    DocumentRequest(
                        path = document
                    )
                )
                if (documentResponse.isSuccessful){
                    documentResponse.body()?.let {
                        documentId = it.id
                    }
                }



                val specialistId = idRole
                val response = TecnisisApi.evaluationService.updateEconomicEvaluation(
                    "Bearer $token",
                    EconomicEvaluationRequest(
                        evaluationDate = evaluationDate,
                        salePrice = salePrice,
                        galleryPercentage = galleryPercentage,
                        specialistId = specialistId,
                        documentId = documentId,
                        status = status
                    ),
                    idEvaluation
                )
                if (response.isSuccessful){
                    response.body()?.let {
                        updateEvaluationSaved(true)
                        updateMessage("Evaluación guardada correctamente")
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