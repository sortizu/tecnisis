package com.example.tecnisis.data.request

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RequestService {
    @GET("requests/v1/api")
    suspend fun getAllRequests(): Response<List<RequestResponse>>
    @GET("requests/v1/api")
    suspend fun getUserRequests(@Query("userId") userId: Int): List<RequestResponse>
    @POST("requests/v1/api")
    suspend fun createRequest(@Body request: CreateRequest): Response<RequestResponse>
    @GET("requests/v1/api")
    suspend fun getRequest(@Query("id") requestId: Long): Response<RequestResponse>

}