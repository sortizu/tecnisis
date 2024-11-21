package com.example.tecnisis.ui.economic_request_evaluation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tecnisis.config.retrofit.TecnisisApi
import com.example.tecnisis.data.evaluations.ArtisticEvaluationRequest
import com.example.tecnisis.data.evaluations.EconomicEvaluationRequest
import kotlinx.coroutines.launch

class EconomicRequestEvaluationViewModel: ViewModel() {
    private val _salePrice = MutableLiveData<Double>()
    val salePrice: LiveData<Double> = _salePrice
    private val _galleryPercentage = MutableLiveData<Double>()
    val galleryPercentage: LiveData<Double> = _galleryPercentage
    private val _document = MutableLiveData<String>()
    val document: LiveData<String> = _document
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message
    fun updateSalePrice(newSalePrice: Double) {
        _salePrice.value = newSalePrice
    }
    fun updateGalleryPercentage(newGalleryPercentage: Double) {
        _galleryPercentage.value = newGalleryPercentage
    }
    fun updateDocument(newDocument: String) {
        _document.value = newDocument
    }
    fun updateMessage(newMessage: String) {
        _message.value = newMessage

    }
    fun saveReview() {
        viewModelScope.launch {
            try{
                val response = TecnisisApi.evaluationService.saveEconomicEvaluation(
                    EconomicEvaluationRequest(
                        evaluationDate = "01/01/2023",
                        salePrice = _salePrice.value!!,
                        galleryPercentage = _galleryPercentage.value!!,
                        specialistId = 0L,
                        document = _document.value!!
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