package com.example.tecnisis.data.request

import com.example.tecnisis.data.artwork.ArtworkResponse
import com.google.gson.annotations.SerializedName

data class RequestResponse(
    val id: Long,
    val date: String,
    val status: String,
    @SerializedName("art_work")
    val artWork: ArtworkResponse
)
