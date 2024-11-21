package com.example.tecnisis.config.navigation
import android.app.Application

class ContextAplication: Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        private var instance: ContextAplication? = null

        fun applicationContext() : ContextAplication {
            return instance as ContextAplication
        }
    }
}