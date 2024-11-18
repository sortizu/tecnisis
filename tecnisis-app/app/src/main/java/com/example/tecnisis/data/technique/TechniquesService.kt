package com.example.tecnisis.data.technique
import retrofit2.Response
import retrofit2.http.GET

interface TechniquesService {
    @GET("techniques")
    suspend fun getTechniques(): Response<List<TechniqueResponse>>

}