package com.example.tecnisis.ui.artistic_request_evaluation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tecnisis.config.retrofit.TecnisisApi
import com.example.tecnisis.data.evaluations.ArtisticEvaluationRequest
import kotlinx.coroutines.launch

class ArtisticRequestEvaluationViewModel: ViewModel() {
    private val _rating = MutableLiveData<Float>()
    val rating: LiveData<Float> = _rating
    private val _result = MutableLiveData<String>()
    val result: LiveData<String> = _result
    private val _reviewDocument = MutableLiveData<String>()
    val reviewDocument: LiveData<String> = _reviewDocument
    private val _date = MutableLiveData<String>()
    val date: LiveData<String> = _date
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message
    fun updateRating(newRating: Float) {
        _rating.value = newRating
    }
    fun updateReviewDocument(newReviewDocument: String) {
        _reviewDocument.value = newReviewDocument
    }
    fun updateDate(newDate: String) {
        _date.value = newDate
    }
    fun updateMessage(newMessage: String) {
        _message.value = newMessage
    }
    fun updateResult(newResult: String) {
        _result.value = newResult
    }

    fun saveReview() {
        viewModelScope.launch {
            try{
                val response = TecnisisApi.evaluationService.saveArtisticEvaluation(
                    ArtisticEvaluationRequest(
                        evaluationDate = _date.value!!,
                        rating = _rating.value!!.toInt(),
                        result = _result.value!!,
                        specialistId = 0L,
                        document = _reviewDocument.value!!
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