package com.example.tecnisis.data.technique
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TechniquesService {
    @GET("techniques/v1/api")
    suspend fun getTechniques(): Response<List<TechniqueResponse>>

    @POST("techniques/v1/api")
    suspend fun saveTechnique(@Body techniqueRequest: TechniqueRequest): Response<TechniqueResponse>

}