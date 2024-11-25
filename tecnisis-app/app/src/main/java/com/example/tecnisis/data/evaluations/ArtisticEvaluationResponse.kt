package com.example.tecnisis.data.evaluations

import com.example.tecnisis.data.document.DocumentResponse
import com.example.tecnisis.data.specialist.SpecialistResponse

data class ArtisticEvaluationResponse(
    val idEvaluation: Long,
    val evaluationDate: String,
    val rating: Int,
    val result: String,
    val specialist: SpecialistResponse,
    val document: DocumentResponse
)
