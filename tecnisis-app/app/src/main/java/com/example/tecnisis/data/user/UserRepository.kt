package com.example.tecnisis.data.user

import com.example.tecnisis.data.person.Persona
import com.example.tecnisis.data.person.PersonaDao

class UserRepository(private val usuarioDao: UsuarioDao, private val personaDao: PersonaDao) {

    suspend fun validateUser(email: String, password: String): Boolean {
        val user = usuarioDao.validateUser(email, password)
        return user != null
    }

    suspend fun registerUserWithPersona(usuario: Usuario, persona: Persona) {
        val userId = usuarioDao.insert(usuario) // Insertar usuario y obtener el ID como Long
        val personaWithUserId = persona.copy(idUsuario = userId.toInt()) // Convertir ID a Int si es necesario
        personaDao.insert(personaWithUserId) // Insertar persona con el ID de usuario asignado
    }
}
