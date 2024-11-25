package com.example.tecnisis.data.person

import com.example.tecnisis.data.user.UserResponse

data class PersonResponse(
    val id: Long,
    val name: String,
    val dni: String,
    val address: String,
    val gender: Char,
    val phone: String,
    val role: String,
    val user: UserResponse
)
