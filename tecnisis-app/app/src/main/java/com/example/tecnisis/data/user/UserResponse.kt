package com.example.tecnisis.data.user

import com.example.tecnisis.data.person.PersonResponse

data class UserResponse(
    val id: Long,
    val email: String,
    val password: String,
    val person: PersonResponse
)
