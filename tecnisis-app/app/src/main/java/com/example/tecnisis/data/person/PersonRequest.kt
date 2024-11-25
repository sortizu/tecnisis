package com.example.tecnisis.data.person

data class PersonRequest(
    val name: String,
    val dni: String,
    val phone: String,
    val address: String,
    val role: String,
    val gender: String
)
