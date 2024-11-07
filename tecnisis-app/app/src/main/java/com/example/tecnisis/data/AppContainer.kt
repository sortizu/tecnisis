package com.example.tecnisis.data

import android.content.Context

interface AppContainer {
    val solicitudRepository: SolicitudRepository
    val userRepository: UserRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val solicitudRepository: SolicitudRepository by lazy {
        SolicitudRepository(
            AppDatabase.getDatabase(context).solicitudDao(),
            AppDatabase.getDatabase(context).obraDao()
        )
    }

    override val userRepository: UserRepository by lazy {
        UserRepository(
            AppDatabase.getDatabase(context).usuarioDao(),
            AppDatabase.getDatabase(context).personaDao()  // Añadido personaDao aquí
        )
    }
}
