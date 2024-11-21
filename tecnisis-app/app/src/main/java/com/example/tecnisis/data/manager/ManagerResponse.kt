package com.example.tecnisis.data.manager

import com.example.tecnisis.data.person.PersonResponse

data class ManagerResponse(
    val idManager: Long,
    val person: PersonResponse
)
