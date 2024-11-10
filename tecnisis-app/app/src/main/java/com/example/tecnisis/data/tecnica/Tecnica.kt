package com.example.tecnisis.data.tecnica

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tecnica")
data class Tecnica(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_tecnica")
    val idTecnica: Int,
    @ColumnInfo(name = "nombre")
    val nombre: String,
    @ColumnInfo(name = "descripcion")
    val descripcion: String
)
