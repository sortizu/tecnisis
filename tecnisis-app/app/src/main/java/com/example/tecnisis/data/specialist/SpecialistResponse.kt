package com.example.tecnisis.data.specialist

import com.example.tecnisis.data.person.PersonResponse
import com.google.gson.annotations.SerializedName

data class SpecialistResponse(
    val id: Long,
    val person: PersonResponse,
    @SerializedName("is_available")
    val isAvailable: Boolean
)
