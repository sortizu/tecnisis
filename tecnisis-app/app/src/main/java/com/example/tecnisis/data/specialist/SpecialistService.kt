package com.example.tecnisis.data.specialist

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SpecialistService {
    @GET("specialists/v1/api")
    suspend fun getSpecialist(@Query("id") id: Long): Response<SpecialistResponse>
}