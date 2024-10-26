package com.example.tecnisis.data

// Esta clase representa el estado de la pantalla de solicitudes del artista
data class ListArtistRequestUiState(
    val isLoading: Boolean = false,
    val solicitudesWithObra: List<SolicitudWithObra> = emptyList(),
    val errorMessage: String? = null
)

// Esta clase almacena la solicitud del artista con su obra asociada
data class SolicitudWithObra(
    val solicitud: Solicitud,
    val obra: Obra
)
