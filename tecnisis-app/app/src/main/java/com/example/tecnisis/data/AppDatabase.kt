package com.example.tecnisis.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.example.tecnisis.data.artist.Artista
import com.example.tecnisis.data.artist.ArtistaDao
import com.example.tecnisis.data.artwork.Obra
import com.example.tecnisis.data.artwork.ObraDao
import com.example.tecnisis.data.person.Persona
import com.example.tecnisis.data.person.PersonaDao
import com.example.tecnisis.data.request.Solicitud
import com.example.tecnisis.data.request.SolicitudDao
import com.example.tecnisis.data.technique.Tecnica
import com.example.tecnisis.data.technique.TecnicaDao
import com.example.tecnisis.data.user.Usuario
import com.example.tecnisis.data.user.UsuarioDao

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
