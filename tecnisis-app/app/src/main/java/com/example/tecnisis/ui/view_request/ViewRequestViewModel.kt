package com.example.tecnisis.ui.view_request

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tecnisis.config.retrofit.TecnisisApi
import com.example.tecnisis.data.evaluations.ArtisticEvaluationResponse
import com.example.tecnisis.data.evaluations.EconomicEvaluationResponse
import com.example.tecnisis.data.request.RequestResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ViewRequestUiState(
    val request: RequestResponse? = null,
    val artisticEvaluation: ArtisticEvaluationResponse? = null,
    val economicEvaluation: EconomicEvaluationResponse? = null,
    val message: String = "",
    val isLoading: Boolean = false
)

class ViewRequestViewModel (requestId: Long): ViewModel()  {
    private val _uiState = MutableStateFlow(ViewRequestUiState())
    val uiState: StateFlow<ViewRequestUiState> = _uiState.asStateFlow()
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    init{
        getRequest(requestId)
    }

    fun updateRequest(request: RequestResponse){
        _uiState.value = _uiState.value.copy(request = request)
    }
    fun updateArtisticEvaluation(evaluation: ArtisticEvaluationResponse){
        _uiState.value = _uiState.value.copy(artisticEvaluation = evaluation)
    }
    fun updateEconomicEvaluation(evaluation: EconomicEvaluationResponse){
        _uiState.value = _uiState.value.copy(economicEvaluation = evaluation)
    }
    fun updateMessage(message: String){
        //_uiState.value = _uiState.value.copy(message = message)
        _message.value = message
    }
    fun updateIsLoading(isLoading: Boolean){
        _uiState.value = _uiState.value.copy(isLoading = isLoading)
    }

    fun getRequest(id: Long){
        updateIsLoading(true)
        viewModelScope.launch {
            try{
                val response = TecnisisApi.requestService.getRequest(id)
                if (response.isSuccessful){
                    response.body()?.let {
                        updateRequest(it[0])
                    }
                }

                else{
                    updateIsLoading(false)
                    return@launch
                }/*
                val artisticResponse = TecnisisApi.evaluationService.getArtisticEvaluation(id)
                if (artisticResponse.isSuccessful){
                    artisticResponse.body()?.let {
                        updateArtisticEvaluation(it)
                    }
                }
                val economicResponse = TecnisisApi.evaluationService.getEconomicEvaluation(id)
                if (economicResponse.isSuccessful) {
                    economicResponse.body()?.let {
                        updateEconomicEvaluation(it)
                    }
                }*/
                updateIsLoading(false)
            }
            catch(e: Exception){

                updateMessage("Error: ${e.message}")
            }
        }
    }
}