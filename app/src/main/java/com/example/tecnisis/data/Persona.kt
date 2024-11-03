package com.example.tecnisis.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "persona",
    foreignKeys = [
        ForeignKey( entity = Usuario::class, parentColumns = ["id_usuario"], childColumns = ["id_usuario"]),
    ]
)
data class Persona(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_persona")
    val idPersona: Int,
    @ColumnInfo(name = "nombre")
    val nombre: String,
    @ColumnInfo(name = "apellido")
    val apellido: String,
    @ColumnInfo(name = "telefono")
    val telefono: String,
    @ColumnInfo(name = "direccion")
    val direccion: String,
    @ColumnInfo(name = "dni")
    val dni: String,
    @ColumnInfo(name = "sexo")
    val sexo: String,
    @ColumnInfo(name = "tipo")
    val tipo: Int, // Tipo de usuario
    @ColumnInfo(name = "id_usuario")
    val idUsuario: Int
)
