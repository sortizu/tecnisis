package com.example.tecnisis.ui.list_artist_requests.data

import retrofit2.http.GET
import retrofit2.http.Query

interface ListUserRequestsService {
    @GET("requests")
    suspend fun getUserRequests(@Query("userId") userId: Int): List<GeneralUserRequestInfo>
}