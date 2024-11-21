package com.example.tecnisis.data.artwork

data class ArtworkRequest(
    val title: String,
    val creationDate: String,
    val image: String,
    val width: Double,
    val height: Double,
    val techniqueId: Long
)
