package com.example.tecnisis

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.tecnisis.data.AppDatabase
import com.example.tecnisis.data.UserRepository
import com.example.tecnisis.ui.theme.TecnisisTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Crear una instancia de UserRepository con los DAOs necesarios
        val database = AppDatabase.getDatabase(applicationContext)
        val userRepository = UserRepository(database.usuarioDao(), database.personaDao())

        setContent {
            TecnisisTheme {
                // Pasar userRepository a TecnisisApp
                TecnisisApp(userRepository = userRepository)
            }
        }
    }
}
