package com.example.tecnisis.data.artwork

data class ArtworkRequest(
    val title: String,
    val creationDate: String,
    val image: String,
    val height: Double,
    val width: Double,
    val artistId: Long,
    val techniqueId: Long
)
