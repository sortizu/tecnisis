package com.example.tecnisis.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

@Database(
    entities = [Usuario::class, Persona::class, Obra::class, Solicitud::class, Tecnica::class, Artista::class], // Agrega Artista aquí
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
    abstract fun personaDao(): PersonaDao
    abstract fun obraDao(): ObraDao
    abstract fun solicitudDao(): SolicitudDao
    abstract fun tecnicaDao(): TecnicaDao // Si tienes un Dao para Tecnica
    abstract fun artistaDao(): ArtistaDao // Si tienes un Dao para Artista

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
