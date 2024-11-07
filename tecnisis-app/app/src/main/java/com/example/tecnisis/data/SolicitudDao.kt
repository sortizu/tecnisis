package com.example.tecnisis.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SolicitudDao {
    @Query("SELECT * FROM solicitud")
    fun getAll(): Flow<List<Solicitud>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(solicitud: Solicitud)

    @Query("SELECT * FROM solicitud WHERE id_solicitud = :id")
    fun getById(id: Int): Flow<Solicitud>

    @Query("SELECT * FROM solicitud AS s JOIN obra AS o ON s.id_obra = o.id_obra WHERE o.id_artista = :userId")
    fun getAllByUserId(userId: Int): Flow<List<Solicitud>>
}