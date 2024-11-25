package com.example.tecnisis.data.request

import com.google.gson.annotations.SerializedName

data class CreateRequest(
    val status: String,
    @SerializedName("art_work_id")
    val artworkId: Long
)
