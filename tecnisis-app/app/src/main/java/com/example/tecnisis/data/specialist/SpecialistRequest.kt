package com.example.tecnisis.data.specialist

import com.google.gson.annotations.SerializedName

data class SpecialistRequest(
    val id: Long,
    @SerializedName("is_available")
    val isAvailable: Boolean
)
