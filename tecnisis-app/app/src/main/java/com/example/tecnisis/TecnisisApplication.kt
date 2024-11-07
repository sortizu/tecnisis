package com.example.tecnisis

import android.app.Application
import com.example.tecnisis.data.AppContainer
import com.example.tecnisis.data.AppDataContainer
import com.example.tecnisis.data.AppDatabase

class TecnisisApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}