package com.example.tecnisis.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.tecnisis.TecnisisApplication
import com.example.tecnisis.ui.list_artist_requests.ListArtistRequestsViewModel

/**
 * Provides Factory to create instance of ViewModel for the entire Inventory app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            ListArtistRequestsViewModel(
                tecnisisApplication().container.solicitudRepository
            )
        }

    }
}

fun CreationExtras.tecnisisApplication(): TecnisisApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as TecnisisApplication)
