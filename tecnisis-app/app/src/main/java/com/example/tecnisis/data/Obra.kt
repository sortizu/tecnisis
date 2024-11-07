package com.example.tecnisis.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "obra",
        foreignKeys = [
            ForeignKey( entity = Tecnica::class, parentColumns = ["id_tecnica"], childColumns = ["id_tecnica"]),
            ForeignKey ( entity = Artista::class, parentColumns = ["id_artista"], childColumns = ["id_artista"])
        ]
)
data class Obra(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_obra")
    val idObra: Int,
    @ColumnInfo(name = "titulo")
    val titulo: String,
    @ColumnInfo(name = "fecha")
    val fecha: Int, // En Millis
    @ColumnInfo(name = "imagen_url")
    val imagenUrl: String,
    @ColumnInfo(name = "ancho")
    val ancho: Double,
    @ColumnInfo(name = "alto")
    val alto: Double,
    @ColumnInfo(name = "id_tecnica")
    val idTecnica: Int,
    @ColumnInfo(name = "id_artista")
    val idArtista: Int
)
