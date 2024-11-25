package com.example.tecnisis.ui.start_request.data

import com.example.tecnisis.data.artwork.ArtworkRequest
import retrofit2.http.POST
import retrofit2.Response
import retrofit2.http.Body

interface StartRequestService {
    @POST("start-request")
    suspend fun startRequest(@Body artworkRequest: ArtworkRequest): Response<String>
}