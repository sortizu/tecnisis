package com.example.tecnisis.ui.login.data
import com.google.gson.annotations.SerializedName
data class LoginResponse(
    val id: Long,
    val role: String,
    @SerializedName("person_id")
    val personId: Long,
    val token: String
)
