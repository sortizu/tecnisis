package com.example.tecnisis.data.evaluations

import com.example.tecnisis.data.document.DocumentResponse
import com.example.tecnisis.data.specialist.SpecialistResponse
import com.google.gson.annotations.SerializedName

data class ArtisticEvaluationResponse(
    val id: Long,
    @SerializedName("evaluation_date")
    val evaluationDate: String,
    val rating: Int,
    val result: String,
    val status: String,
    val specialist: SpecialistResponse,
    val document: DocumentResponse
)
