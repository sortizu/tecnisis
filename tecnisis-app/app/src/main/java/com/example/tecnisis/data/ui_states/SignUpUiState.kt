package com.example.tecnisis.data.ui_states

import com.example.tecnisis.data.person.Persona

data class SignUpUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSignUpSuccessful: Boolean = false,
    //val usuario: Usuario? = null,
    val persona: Persona? = null
)
