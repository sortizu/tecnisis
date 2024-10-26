package com.example.tecnisis.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tecnisis.data.ListArtistRequestUiState
import com.example.tecnisis.data.Obra
import com.example.tecnisis.data.Solicitud
import com.example.tecnisis.data.SolicitudWithObra
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ListArtistRequestsViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(ListArtistRequestUiState())
    val uiState: StateFlow<ListArtistRequestUiState> = _uiState.asStateFlow()

    init {
        loadArtistRequests()
    }

    // Actualiza la lista de solicitudes del artista en ListArtistRequestsUiState
    // lo que ejecutara el evento de recomposición de la UI
    fun loadArtistRequests() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)

                // Datos de prueba hasta que se puedan realizar las llamadas a la base de datos
                // Obras
                var obra1 = Obra(1, "Obra 1", "2023-01-01", "imagen1.jpg", 100.0, 200.0, 1, 1)
                var obra2 = Obra(2, "Obra 2", "2023-02-01", "imagen2.jpg", 150.0, 250.0, 2, 1)
                // Solicitudes
                var solicitud1 = Solicitud(1, "Pendiente", "2023-03-01", 1)
                var solicitud2 = Solicitud(2, "Aprobada", "2023-04-01", 2)

                var solicitudesWithObra = listOf(
                        SolicitudWithObra(solicitud1, obra1),
                        SolicitudWithObra(solicitud2, obra2))

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    solicitudesWithObra = solicitudesWithObra
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