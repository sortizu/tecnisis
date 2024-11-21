package com.example.tecnisis.ui.login.data

data class LoginResponse(
    val userId: Int,
    val role: String,
    val success: Boolean,
    val message: String
)
