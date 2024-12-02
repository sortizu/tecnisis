package com.example.tecnisis.data.person

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PersonService {
    @GET("persons/v1/api/{id}")
    suspend fun getPerson(@Header("Authorization") token: String, @Path("id") personId: Long): Response<PersonResponse>
    @POST("persons/v1/api")
    suspend fun updatePerson(@Header("Authorization") token: String,@Body request: PersonRequest, @Query("id") personId: Long): Response<PersonResponse>

}