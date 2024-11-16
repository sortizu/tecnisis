package com.example.tecnisis.data.specialist

import com.example.tecnisis.data.person.PersonResponse

data class SpecialistResponse(
    val idSpecialist: Long,
    val person: PersonResponse,
    val availability: Boolean
)
