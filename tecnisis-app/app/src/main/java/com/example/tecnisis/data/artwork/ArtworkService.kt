package com.example.tecnisis.data.artwork

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ArtworkService {
    @POST("artworks/v1/api")
    suspend fun saveArtwork(@Body artworkRequest: ArtworkRequest): Response<ArtworkResponse>
}