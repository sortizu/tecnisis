package com.example.tecnisis.data.artwork

import com.example.tecnisis.data.artist.ArtistResponse
import com.example.tecnisis.data.technique.TechniqueResponse
import com.google.gson.annotations.SerializedName

data class ArtworkResponse(
    val id: Long,
    val title: String,
    @SerializedName("creation_date")
    val creationDate: String,
    val image: String,
    val width: Double,
    val height: Double,
    val technique: TechniqueResponse,
    val artist: ArtistResponse
)
