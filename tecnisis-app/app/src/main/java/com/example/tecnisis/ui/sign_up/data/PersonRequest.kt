package com.example.tecnisis.ui.sign_up.data

data class PersonRequest(
    val name: String,
    val surnames: String,
    val dni: String,
    val phone: String,
    val address: String,
    val sex: String,
    val userRequest: UserRequest
)
