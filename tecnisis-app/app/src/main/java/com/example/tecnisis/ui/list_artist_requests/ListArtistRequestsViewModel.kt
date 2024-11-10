package com.example.tecnisis.ui.list_artist_requests

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tecnisis.config.datastore.DataStoreManager
import com.example.tecnisis.config.retrofit.TecnisisApi

import com.example.tecnisis.data.obra.Obra
import com.example.tecnisis.data.solicitud.Solicitud
import com.example.tecnisis.data.solicitud.SolicitudRepository
import com.example.tecnisis.ui.list_artist_requests.data.GeneralUserRequestInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class ListArtistRequestUiState(
    val isLoading: Boolean = false,
    val requests: List<GeneralUserRequestInfo> = emptyList(),
    val errorMessage: String? = null
)

class ListArtistRequestsViewModel(): ViewModel() {
    private val _uiState = MutableStateFlow(ListArtistRequestUiState())
    val uiState: StateFlow<ListArtistRequestUiState> = _uiState.asStateFlow()


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
                TecnisisApi.listUserRequestsService.getUserRequests(id).let {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        requests = it
                    )
                }

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message
                )
            }
        }
    }
}