package com.example.tecnisis.data.artwork

import com.example.tecnisis.data.technique.TechniqueResponse

data class ArtworkResponse(
    val idArtwork: Long,
    val title: String,
    val creationDate: String,
    val imageAddress: String,
    val width: Double,
    val height: Double,
    val technique: TechniqueResponse,
)
