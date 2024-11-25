package com.example.tecnisis.data.specialist

import com.example.tecnisis.data.person.PersonResponse

data class SpecialistResponse(
    val id: Long,
    val person: PersonResponse,
    val availability: Boolean
)
