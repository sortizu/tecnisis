package com.example.tecnisis.data

import android.content.Context

interface AppContainer {
    val solicitudRepository: SolicitudRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [ItemsRepository]
     */
    override val solicitudRepository: SolicitudRepository by lazy {
        SolicitudRepository(AppDatabase.getDatabase(context).solicitudDao(), AppDatabase.getDatabase(context).obraDao())
    }
}