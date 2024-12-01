package com.example.tecnisis.data.evaluations

import com.google.gson.annotations.SerializedName


data class ArtisticEvaluationRequest(
    @SerializedName("evaluation_date")
    val evaluationDate: String,
    val rating: Int,
    val result: String,
    val status: String,
    val specialistId: Long,
    @SerializedName("document_id")
    val documentId: Long
)
