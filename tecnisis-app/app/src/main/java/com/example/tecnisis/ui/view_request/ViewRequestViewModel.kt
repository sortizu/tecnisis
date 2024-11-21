package com.example.tecnisis.ui.view_request

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tecnisis.config.datastore.DataStoreManager
import com.example.tecnisis.config.retrofit.TecnisisApi
import com.example.tecnisis.data.request.GeneralRequestInfoResponse
import kotlinx.coroutines.launch

class ViewRequestViewModel: ViewModel()  {
    private val _request = MutableLiveData<GeneralRequestInfoResponse>()
    val request: LiveData<GeneralRequestInfoResponse> = _request
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getRequest(id: Int){
        _isLoading.value = true
        viewModelScope.launch {
            try{
                val response = TecnisisApi.viewRequestService.getGeneralRequestInfo(id)
                if (response.isSuccessful){
                    response.body()?.let {
                        _request.value = it
                        _isLoading.value = false
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