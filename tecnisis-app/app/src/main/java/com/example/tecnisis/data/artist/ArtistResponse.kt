package com.example.tecnisis.data.artist

import com.example.tecnisis.data.person.PersonResponse

data class ArtistResponse (
    val id: Long,
    val person: PersonResponse,
)