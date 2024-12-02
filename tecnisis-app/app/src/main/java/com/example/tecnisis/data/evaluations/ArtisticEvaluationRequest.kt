package com.example.tecnisis.data.evaluations

import com.google.gson.annotations.SerializedName


data class ArtisticEvaluationRequest(
    @SerializedName("evaluation_date")
    val evaluationDate: String,
    val rating: Double,
    val result: String,
    val status: String,
    @SerializedName("specialist_id")
    val specialistId: Long,
    @SerializedName("document_id")
    val documentId: Long
)
