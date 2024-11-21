package com.example.tecnisis.data.evaluations

data class EconomicEvaluationRequest(
    val evaluationDate: String,
    val salePrice: Double,
    val galleryPercentage: Double,
    val specialistId: Long,
    val document: String
)
