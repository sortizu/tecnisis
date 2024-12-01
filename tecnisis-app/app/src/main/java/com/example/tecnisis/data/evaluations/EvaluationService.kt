package com.example.tecnisis.data.evaluations
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface EvaluationService {
    @POST("artistic-evaluations/v1/api/{id}")
    suspend fun updateArtisticEvaluation(@Header("Authorization") token: String, @Body evaluationRequest: ArtisticEvaluationRequest, @Path("id") id: Long): Response<ArtisticEvaluationResponse>
    @POST("economic-evaluations/v1/api/{id}")
    suspend fun updateEconomicEvaluation(@Header("Authorization") token: String, @Body evaluationRequest: EconomicEvaluationRequest, @Path("id") id: Long): Response<EconomicEvaluationResponse>
    @GET("artistic-evaluations/v1/api/requests/{id}")
    suspend fun getArtisticEvaluationByRequest(@Header("Authorization") token: String, @Path("id") id: Long): Response<ArtisticEvaluationResponse>
    @GET("economic-evaluations/v1/api/requests/{id}")
    suspend fun getEconomicEvaluationByRequest(@Header("Authorization") token: String, @Path("id") id: Long): Response<ArtisticEvaluationResponse>
}