package com.example.tecnisis.data.evaluations
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response
interface EvaluationService {
    @POST("artistic_evaluation")
    suspend fun saveArtisticEvaluation(@Body evaluationRequest: ArtisticEvaluationRequest): Response<String>
    @POST("economic_evaluation")
    suspend fun saveEconomicEvaluation(@Body evaluationRequest: EconomicEvaluationRequest): Response<String>
}