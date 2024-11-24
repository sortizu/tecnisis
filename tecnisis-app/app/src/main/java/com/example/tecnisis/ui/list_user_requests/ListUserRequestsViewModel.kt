package com.example.tecnisis.ui.list_user_requests

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tecnisis.config.datastore.DataStoreManager
import com.example.tecnisis.config.retrofit.TecnisisApi
import com.example.tecnisis.data.request.RequestResponse

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class ListUserRequestUiState(
    val isLoading: Boolean = false,
    val requests: List<RequestResponse> = emptyList(),
    val message: String = "",
    val role: String = ""
)

class ListUserRequestsViewModel(): ViewModel() {
    private val _uiState = MutableStateFlow(ListUserRequestUiState())
    val uiState: StateFlow<ListUserRequestUiState> = _uiState.asStateFlow()
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message



    fun resetMessage() {
        //_uiState.value = _uiState.value.copy(message = "")
        _message.value = ""
    }

    fun updateMessage(message: String) {
        _uiState.value = _uiState.value.copy(message = message)
    }

    fun loadRole(dataStoreManager: DataStoreManager) {
        viewModelScope.launch {
            dataStoreManager.role.first()?.let {
                _uiState.value = _uiState.value.copy(role = it)
            }
        }
    }

    // Updates the UI state with a new list of requests
    fun loadArtistRequests(dataStoreManager: DataStoreManager) {

        viewModelScope.launch {
            var id = -1
            dataStoreManager.id.first()?.let {
                id = it.toInt()
            }
            if (id == -1) {
                return@launch
            }
            _uiState.value = _uiState.value.copy(isLoading = true)
            /*
            First version
            TecnisisApi.requestService.getUserRequests(id).let {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    requests = it
                )
            }*/

            val newState = try {
                val response = TecnisisApi.requestService.getAllRequests()
                if (response.isSuccessful) {
                    response.body()?.let { requests ->
                        _uiState.value.copy(isLoading = false, requests = requests, message = "")
                    } ?: _uiState.value.copy(isLoading = false, message = "No se pudo cargar las solicitudes")
                } else {
                    _uiState.value.copy(isLoading = false, message = "No se pudo cargar las solicitudes")
                }
            } catch (e: Exception) {
                _uiState.value.copy(isLoading = false, message = "Error de conexión: ${e.message}")
            }

            _uiState.value = newState
        }
    }
}