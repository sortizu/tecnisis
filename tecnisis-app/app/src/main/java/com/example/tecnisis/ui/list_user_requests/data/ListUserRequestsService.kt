package com.example.tecnisis.ui.list_user_requests.data

import com.example.tecnisis.data.request.RequestResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ListUserRequestsService {
    @GET("requests")
    suspend fun getUserRequests(@Query("userId") userId: Int): List<RequestResponse>
}