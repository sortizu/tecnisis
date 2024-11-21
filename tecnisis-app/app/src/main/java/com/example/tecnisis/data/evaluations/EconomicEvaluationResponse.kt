package com.example.tecnisis.data.evaluations

import com.example.tecnisis.data.document.DocumentResponse
import com.example.tecnisis.data.specialist.SpecialistResponse

data class EconomicEvaluationResponse(
    val idEvaluation: Long,
    val evaluationDate: String,
    val salePrice: Double,
    val galleryPercentage: Double,
    val specialist: SpecialistResponse,
    val document: DocumentResponse
)
