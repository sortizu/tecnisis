package com.example.tecnisis.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Solicitud::class, Obra::class, Persona::class, Artista::class, Tecnica::class, Usuario::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun solicitudDao(): SolicitudDao
    abstract fun obraDao(): ObraDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also {
                        INSTANCE = it
                    }
            }
        }
    }
}