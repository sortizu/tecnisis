package com.example.tecnisis.data.person

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface PersonaDao {

    // Insertar una nueva persona
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(persona: Persona)

    // Actualizar los datos de una persona
    @Update
    suspend fun update(persona: Persona)

    // Obtener todas las personas
    @Query("SELECT * FROM persona")
    suspend fun getAllPersonas(): List<Persona>

    // Obtener una persona por ID de persona
    @Query("SELECT * FROM persona WHERE id_persona = :idPersona")
    suspend fun getPersonaById(idPersona: Int): Persona?

    // Obtener personas por ID de usuario
    @Query("SELECT * FROM persona WHERE id_usuario = :idUsuario")
    suspend fun getPersonasByUsuarioId(idUsuario: Int): List<Persona>

    // Eliminar una persona por ID
    @Query("DELETE FROM persona WHERE id_persona = :idPersona")
    suspend fun deletePersonaById(idPersona: Int)
}
