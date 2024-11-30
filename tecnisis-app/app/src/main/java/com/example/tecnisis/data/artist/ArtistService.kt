package com.example.tecnisis.data.artist

import com.example.tecnisis.data.request.RequestResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ArtistService {
    @GET("artists/v1/api/requests/{id}")
    suspend fun getArtistRequests(@Header("Authorization") token: String, @Path("id") id: Long): Response<List<RequestResponse>>
}