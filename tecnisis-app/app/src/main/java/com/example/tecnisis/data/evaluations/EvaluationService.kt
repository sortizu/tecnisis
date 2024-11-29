package com.example.tecnisis.data.evaluations
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface EvaluationService {
    @POST("artistic_evaluation")
    suspend fun saveArtisticEvaluation(@Header("Authorization") token: String, @Body evaluationRequest: ArtisticEvaluationRequest): Response<String>
    @POST("economic_evaluation")
    suspend fun saveEconomicEvaluation(@Header("Authorization") token: String, @Body evaluationRequest: EconomicEvaluationRequest): Response<String>
    @GET
    suspend fun getArtisticEvaluation(@Header("Authorization") token: String, @Query ("id") id: Long ): Response<ArtisticEvaluationResponse>
    @GET
    suspend fun getEconomicEvaluation(@Header("Authorization") token: String, @Query ("id") id: Long ): Response<EconomicEvaluationResponse>

}