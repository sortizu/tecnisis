package com.example.tecnisis.data.evaluations

import com.example.tecnisis.data.document.DocumentResponse
import com.example.tecnisis.data.specialist.SpecialistResponse
import com.google.gson.annotations.SerializedName

data class EconomicEvaluationResponse(
    val id: Long,
    @SerializedName("evaluation_date")
    val evaluationDate: String,
    @SerializedName("sales_price")
    val salesPrice: Double,
    @SerializedName("gallery_percentage")
    val galleryPercentage: Double,
    val status: String,
    val specialist: SpecialistResponse,
    val document: DocumentResponse
)
