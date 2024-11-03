package com.example.tecnisis.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

import com.example.tecnisis.TecnisisApplication

/**
 * Provides Factory to create instance of ViewModel for the entire Inventory app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for ItemEditViewModel
        initializer {
            ListArtistRequestsViewModel(
                tecnisisApplication().container.solicitudRepository
            )
        }
    }
}

fun CreationExtras.tecnisisApplication(): TecnisisApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as TecnisisApplication)
