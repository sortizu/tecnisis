package com.example.tecnisis.data.person

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PersonService {
    @GET("persons/v1/api")
    suspend fun getPerson(@Query("id") personId: Long): Response<PersonResponse>
    @POST("persons/v1/api")
    suspend fun updatePerson(@Body request: PersonRequest, @Query("id") personId: Long): Response<PersonResponse>

}