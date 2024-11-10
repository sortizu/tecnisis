package com.example.tecnisis.data.tecnica

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TecnicaDao {

    // Insertar una nueva técnica
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tecnica: Tecnica)

    // Actualizar una técnica existente
    @Update
    suspend fun update(tecnica: Tecnica)

    // Obtener todas las técnicas
    @Query("SELECT * FROM tecnica")
    suspend fun getAllTecnicas(): List<Tecnica>

    // Obtener una técnica por ID
    @Query("SELECT * FROM tecnica WHERE id_tecnica = :idTecnica")
    suspend fun getTecnicaById(idTecnica: Int): Tecnica?

    // Eliminar una técnica por ID
    @Query("DELETE FROM tecnica WHERE id_tecnica = :idTecnica")
    suspend fun deleteTecnicaById(idTecnica: Int)
}
