package com.example.tecnisis.data

data class Obra(
    val id: Int,
    val titulo: String,
    val fecha: String,
    val imagenUrl: String,
    val ancho: Double,
    val alto: Double,
    val tecnicaId: Int,
    val artistaId: Int
)
