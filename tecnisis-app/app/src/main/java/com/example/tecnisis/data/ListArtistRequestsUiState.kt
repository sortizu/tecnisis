package com.example.tecnisis.data

data class ListArtistRequestUiState(
    val isLoading: Boolean = false,
    val solicitudesWithObras: Map<Solicitud, Obra> = emptyMap(), // Change to Map
    val errorMessage: String? = null
)

// Esta clase almacena la solicitud del artista con su obra asociada
/*data class SolicitudWithObra(
    val solicitud: Solicitud,
    val obra: Obra
)*/
