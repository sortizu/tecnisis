package com.example.tecnisis.data.solicitud

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.tecnisis.data.obra.Obra

@Entity(tableName = "solicitud",
        foreignKeys = [
            ForeignKey( entity = Obra::class, parentColumns = ["id_obra"], childColumns = ["id_obra"]),
        ]
)
data class Solicitud (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_solicitud")
    val idSolicitud: Int,
    @ColumnInfo(name = "estado")
    val estado: String,
    @ColumnInfo(name = "fecha")
    val fecha: Int, // En Millis
    @ColumnInfo(name = "id_obra")
    val idObra: Int
)