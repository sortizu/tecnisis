package com.example.tecnisis.data.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UsuarioDao {
    @Insert
    suspend fun insert(usuario: Usuario): Long // Cambiar a Long para obtener el ID generado

    @Query("SELECT * FROM usuario WHERE email = :email AND password = :password")
    suspend fun validateUser(email: String, password: String): Usuario?
}
