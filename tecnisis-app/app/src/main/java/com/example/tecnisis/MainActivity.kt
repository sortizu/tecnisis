package com.example.tecnisis

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.tecnisis.ui.theme.TecnisisTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TecnisisTheme {
                // Pasar userRepository a TecnisisApp
                TecnisisApp()
            }
        }
    }
}
