package com.example.tecnisis.data.artwork

import com.example.tecnisis.data.artist.ArtistResponse
import com.example.tecnisis.data.technique.TechniqueResponse

data class ArtworkResponse(
    val id: Long,
    val title: String,
    val creationDate: String,
    val image: String,
    val width: Double,
    val height: Double,
    val technique: TechniqueResponse,
    val artist: ArtistResponse
)
