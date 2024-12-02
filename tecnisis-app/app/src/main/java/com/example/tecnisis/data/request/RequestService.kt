package com.example.tecnisis.data.request

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface RequestService {
    @GET("requests/v1/api")
    suspend fun getAllRequests(@Header("Authorization") token: String): Response<List<RequestResponse>>
    @POST("requests/v1/api")
    suspend fun createRequest(@Header("Authorization") token: String, @Body request: CreateRequest): Response<RequestResponse>
    @GET("requests/v1/api")
    suspend fun getRequest(@Header("Authorization") token: String, @Query("id") requestId: Long): Response<List<RequestResponse>>

}