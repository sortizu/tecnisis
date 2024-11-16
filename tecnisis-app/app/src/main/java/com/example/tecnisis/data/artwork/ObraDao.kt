package com.example.tecnisis.data.artwork

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ObraDao {
    @Query("SELECT * FROM obra")
    fun getAll(): Flow<List<Obra>>

    @Query("SELECT * FROM obra WHERE id_obra = :id")
    fun getById(id: Int): Flow<Obra>

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(obra: Obra)
}