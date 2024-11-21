package com.example.tecnisis.data.artist

import com.example.tecnisis.data.person.PersonResponse

data class ArtistResponse (
    val idArtist: Long,
    val person: PersonResponse,
)