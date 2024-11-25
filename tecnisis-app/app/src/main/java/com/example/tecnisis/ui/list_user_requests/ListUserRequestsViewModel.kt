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
    val filter: String = "",
    val message: String = "",
    val role: String = ""
)

class ListUserRequestsViewModel(dataStoreManager: DataStoreManager): ViewModel() {
    private val _uiState = MutableStateFlow(ListUserRequestUiState())
    val uiState: StateFlow<ListUserRequestUiState> = _uiState.asStateFlow()
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    init {
        var userId = ""
        var role = ""
        viewModelScope.launch {
            dataStoreManager.id.let {
                userId = it.first()!!
            }
            dataStoreManager.role.let {
                role = it.first()!!
            }
        }
        updateRole(role)
        loadArtistRequests(userId.toLong())
    }

    fun resetMessage() {
        //_uiState.value = _uiState.value.copy(message = "")
        _message.value = ""
    }

    fun updateMessage(message: String) {
        _uiState.value = _uiState.value.copy(message = message)
    }
    fun updateRole(role: String) {
        _uiState.value = _uiState.value.copy(role = role)
    }
    fun updateFilter(filter: String) {
        _uiState.value = _uiState.value.copy(filter = filter)
    }
    fun getFilteredRequests(query: String) : List<RequestResponse> {
        val filteredRequests = _uiState.value.requests.filter { request ->
            request.artWork.title.contains(query, ignoreCase = true)
        }
        return filteredRequests
    }

    // Updates the UI state with a new list of requests
    fun loadArtistRequests(id: Long){
        viewModelScope.launch {
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