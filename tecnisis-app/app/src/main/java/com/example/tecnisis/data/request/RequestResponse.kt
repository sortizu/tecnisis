package com.example.tecnisis.data.request

import com.example.tecnisis.data.artwork.ArtworkResponse

data class RequestResponse(
    val idRequest: Long,
    val request_date: String,
    val status: String,
    val artwork: ArtworkResponse
)
