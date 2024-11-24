package com.example.tecnisis.ui.view_request.data

import com.example.tecnisis.data.request.GeneralRequestInfoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ViewRequestService {
    @GET("requests/v1/api")
    suspend fun getGeneralRequestInfo(@Query("id") requestId: Int): Response<GeneralRequestInfoResponse>
}