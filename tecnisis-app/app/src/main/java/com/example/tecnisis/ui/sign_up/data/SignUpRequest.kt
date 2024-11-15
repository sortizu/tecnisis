package com.example.tecnisis.ui.sign_up.data

data class SignUpRequest(
    val email: String,
    val password: String,
    val name: String,
    val idNumber: String,
    val address: String,
    val gender: String,
    val phone: String,
    val userRole: String
)
