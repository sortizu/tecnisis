package com.example.tecnisis.data.evaluations

import com.google.gson.annotations.SerializedName

data class EconomicEvaluationRequest(
    @SerializedName("evaluation_date")
    val evaluationDate: String,
    @SerializedName("sale_price")
    val salePrice: Double,
    @SerializedName("gallery_percentage")
    val galleryPercentage: Double,
    val status: String,
    @SerializedName("specialist_id")
    val specialistId: Long,
    @SerializedName("document_id")
    val documentId: Long
)
