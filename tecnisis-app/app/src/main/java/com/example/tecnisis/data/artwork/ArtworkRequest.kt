package com.example.tecnisis.data.artwork

import com.google.gson.annotations.SerializedName

data class ArtworkRequest(
    val title: String,
    val creationDate: String,
    val image: String,
    val height: Double,
    val width: Double,
    @SerializedName("artist_id")
    val artistId: Long,
    @SerializedName("technique_id")
    val techniqueId: Long
)
