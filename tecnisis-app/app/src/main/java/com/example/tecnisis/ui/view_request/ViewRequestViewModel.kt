package com.example.tecnisis.ui.view_request

import androidx.lifecycle.ViewModel

data class ViewRequestUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class ViewRequestViewModel: ViewModel()  {

}