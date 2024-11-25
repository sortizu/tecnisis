package com.example.tecnisis.data.evaluations
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface EvaluationService {
    @POST("artistic_evaluation")
    suspend fun saveArtisticEvaluation(@Body evaluationRequest: ArtisticEvaluationRequest): Response<String>
    @POST("economic_evaluation")
    suspend fun saveEconomicEvaluation(@Body evaluationRequest: EconomicEvaluationRequest): Response<String>
    @GET
    suspend fun getArtisticEvaluation(@Query ("id") id: Long ): Response<ArtisticEvaluationResponse>
    @GET
    suspend fun getEconomicEvaluation(@Query ("id") id: Long ): Response<EconomicEvaluationResponse>

}