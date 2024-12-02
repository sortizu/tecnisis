package com.example.tecnisis.data.specialist

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import com.example.tecnisis.data.request.RequestResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path

interface SpecialistService {
    @GET("specialists/v1/api")
    suspend fun getSpecialist(@Header("Authorization") token: String, @Query("id") id: Long): Response<SpecialistResponse>

    @GET("specialists/v1/api/artisticRequests/{id}")
    suspend fun getArtisticRequests(@Header("Authorization") token: String, @Path("id") id: Long): Response<List<RequestResponse>>

    @GET("specialists/v1/api/economicRequests/{id}")
    suspend fun getEconomicRequests(@Header("Authorization") token: String, @Path("id") id: Long): Response<List<RequestResponse>>

    @PUT("specialists/v1/api/{id}")
    suspend fun updateSpecialist(@Header("Authorization") token: String, @Path("id") id: Long, @Body specialist: SpecialistRequest): Response<SpecialistResponse>


}