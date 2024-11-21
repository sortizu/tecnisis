package com.example.tecnisis.data.request

import com.example.tecnisis.data.evaluations.ArtisticEvaluationResponse
import com.example.tecnisis.data.evaluations.EconomicEvaluationResponse

data class GeneralRequestInfoResponse(
    val request: RequestResponse,
    val artisticEvaluation: ArtisticEvaluationResponse,
    val economicEvaluation: EconomicEvaluationResponse,
)
