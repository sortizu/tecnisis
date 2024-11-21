package com.example.tecnisis.data.evaluations


data class ArtisticEvaluationRequest(
    val evaluationDate: String,
    val rating: Int,
    val result: String,
    val specialistId: Long,
    val document: String
)
