package com.example.tecnisis.ui.list_user_requests

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tecnisis.config.datastore.DataStoreManager
import com.example.tecnisis.config.retrofit.TecnisisApi

import com.example.tecnisis.ui.list_user_requests.data.GeneralUserRequestInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class ListUserRequestUiState(
    val isLoading: Boolean = false,
    val requests: List<GeneralUserRequestInfo> = emptyList(),
    val errorMessage: String? = null
)

class ListUserRequestsViewModel(): ViewModel() {
    private val _uiState = MutableStateFlow(ListUserRequestUiState())
    val uiState: StateFlow<ListUserRequestUiState> = _uiState.asStateFlow()
    private val _role = MutableStateFlow("")
    val role: StateFlow<String> = _role.asStateFlow()

    fun loadRole(dataStoreManager: DataStoreManager) {
        viewModelScope.launch {
            dataStoreManager.role.first()?.let {
                _role.value = it
            }
        }
    }

    // Updates the UI state with a new list of requests
    fun loadArtistRequests(dataStoreManager: DataStoreManager) {
        viewModelScope.launch {
            try {
                var id = -1
                dataStoreManager.id.first()?.let {
                    id = it.toInt()
                }
                if (id == -1) {
                    return@launch
                }
                _uiState.value = _uiState.value.copy(isLoading = true)
                /*TecnisisApi.listUserRequestsService.getUserRequests(id).let {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        requests = it
                    )
                }*/
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    requests = List(1){
                        GeneralUserRequestInfo(
                            1,"Montañas nevadas","","","Aprobado"
                        )
                    }
                )

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message
                )
            }
        }
    }
}