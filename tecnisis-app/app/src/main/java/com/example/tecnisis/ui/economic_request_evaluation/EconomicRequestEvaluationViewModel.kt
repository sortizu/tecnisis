package com.example.tecnisis.ui.economic_request_evaluation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tecnisis.config.datastore.DataStoreManager
import com.example.tecnisis.config.retrofit.TecnisisApi
import com.example.tecnisis.data.document.DocumentRequest
import com.example.tecnisis.data.evaluations.EconomicEvaluationRequest
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

data class EconomicRequestReviewUiState(
    val salePrice: Double = 0.0,
    val galleryPercentage: Double = 0.0,
    val document: String = "",
    val request: RequestResponse? = null,
    val evaluationSaved: Boolean = false,
    val isLoading: Boolean = false,
    val date: String = "",
)

class EconomicRequestEvaluationViewModel(idRequest: Long, evaluationStatus: String, dataStoreManager: DataStoreManager): ViewModel() {

    private val _uiState = MutableStateFlow(EconomicRequestReviewUiState())
    val uiState: StateFlow<EconomicRequestReviewUiState> = _uiState.asStateFlow()
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message
    private val dataStoreManager: DataStoreManager = dataStoreManager

    init {
        if (evaluationStatus != "PENDING"){
            loadEvaluation(idRequest)
        }
        getRequest(idRequest)
        updateDate(System.currentTimeMillis())
    }

    fun updateSalePrice(newSalePrice: Double) {
        _uiState.value = _uiState.value.copy(salePrice = newSalePrice)
    }
    fun updateGalleryPercentage(newGalleryPercentage: Double) {
        _uiState.value = _uiState.value.copy(galleryPercentage = newGalleryPercentage)
    }
    fun updateReviewDocument(newReviewDocument: String) {
        _uiState.value = _uiState.value.copy(document = newReviewDocument)
    }
    fun updateRequest(newRequest: RequestResponse) {
        _uiState.value = _uiState.value.copy(request = newRequest)
    }
    fun updateDate(newDateInMillis: Long) {
        _uiState.value = _uiState.value.copy(date = convertMillisToDate(newDateInMillis))
    }
    fun updateMessage(newMessage: String) {
        _message.value = newMessage
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
            val response = TecnisisApi.evaluationService.getEconomicEvaluationByRequest(
                "Bearer $token",
                idRequest
            )
            if (response.isSuccessful) {
                response.body()?.let {
                    updateSalePrice(it.salesPrice)
                    updateGalleryPercentage(it.galleryPercentage)
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
                val response = TecnisisApi.requestService.getRequest("Bearer $token",id)
                if (response.isSuccessful) {
                    response.body()?.let {
                        updateRequest(it)
                    }
                } else {
                    _message.value = response.message()
                    return@launch
                }
                updateLoading(false)
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
                val evaluationResponse = TecnisisApi.evaluationService.getEconomicEvaluationByRequest("Bearer $token", _uiState.value.request!!.id)
                if (evaluationResponse.isSuccessful){
                    evaluationResponse.body()?.let {
                        idEvaluation = it.id
                    }
                }

                var status = "APPROVED"
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
                }else{
                    updateMessage("No se pudo guardar el documento")
                    return@launch
                }

                // change date format to yyyy-MM-dd
                val date = evaluationDate.split("/").reversed().joinToString("-")

                val specialistId = idRole
                val response = TecnisisApi.evaluationService.updateEconomicEvaluation(
                    "Bearer $token",
                    EconomicEvaluationRequest(
                        evaluationDate = date,
                        salePrice = salePrice,
                        galleryPercentage = galleryPercentage,
                        specialistId = specialistId,
                        documentId = documentId,
                        status = status,
                        requestId = _uiState.value.request!!.id
                    ),
                    idEvaluation
                )
                if (!response.isSuccessful){
                    val errorBody = JSONObject(response.errorBody()?.string()!!)
                    updateMessage(errorBody.toString())
                    return@launch
                }
                // Update request
                val requestResponse = TecnisisApi.requestService.updateRequest(
                    "Bearer $token",
                    CreateRequest(
                        status = "APPROVED",
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