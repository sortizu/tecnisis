package com.example.tecnisis.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "artista",
    foreignKeys = [
        ForeignKey( entity = Persona::class, parentColumns = ["id_persona"], childColumns = ["id_persona"]),
    ]
)
data class Artista(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_artista")
    val idArtista: Int,
    @ColumnInfo(name = "id_persona")
    val idPersona: Int
)
