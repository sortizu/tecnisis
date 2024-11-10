package com.example.tecnisis.ui.list_artist_requests

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tecnisis.data.ui_states.ListArtistRequestUiState
import com.example.tecnisis.data.obra.Obra
import com.example.tecnisis.data.solicitud.Solicitud
import com.example.tecnisis.data.solicitud.SolicitudRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ListArtistRequestsViewModel(private val repository: SolicitudRepository): ViewModel() {
    private val _uiState = MutableStateFlow(ListArtistRequestUiState())
    val uiState: StateFlow<ListArtistRequestUiState> = _uiState.asStateFlow()


    // Actualiza la lista de solicitudes del artista en ListArtistRequestsUiState
    // lo que ejecutara el evento de recomposición de la UI
    fun loadArtistRequests() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)

                repository.getSolicitudesWithObras().collect { solicitudesWithObras : Map<Solicitud, Obra> ->
                    // Update UI state with solicitudesWithObras
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        solicitudesWithObras = solicitudesWithObras // Assuming you update uiState to handle Map
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
/*
    companion object {
        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                // Imprimir a logcat para testear
                Log.d("ListArtistRequestsViewModel", "Factory initialized----------------------")

                val application = (this[APPLICATION_KEY] as TecnisisApplication)
                ListArtistRequestsViewModel(application.container.solicitudRepository)
            }
        }
    }
*/
}