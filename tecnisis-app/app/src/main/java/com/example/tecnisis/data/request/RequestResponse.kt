package com.example.tecnisis.data.request

import com.example.tecnisis.data.artwork.ArtworkResponse

data class RequestResponse(
    val id: Long,
    val date: String,
    val status: String,
    val artWork: ArtworkResponse
)
