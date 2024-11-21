package com.example.tecnisis.ui.view_request.data

import com.example.tecnisis.data.request.GeneralRequestInfoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ViewRequestService {
    @GET("requests")
    suspend fun getGeneralRequestInfo(@Query("requestId") requestId: Int): Response<GeneralRequestInfoResponse>
}