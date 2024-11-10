package com.example.tecnisis.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tecnisis.data.user.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    var email: String = ""
        private set

    var password: String = ""
        private set

    var loginError: String? = null
        private set

    var isLoginSuccessful: Boolean = false
        private set

    // Función para actualizar el email
    fun onEmailChange(updatedEmail: String) {
        email = updatedEmail
    }

    // Función para actualizar el password
    fun onPasswordChange(updatedPassword: String) {
        password = updatedPassword
    }

    // Función para validar el usuario con el repositorio
    fun validateUser() {
        viewModelScope.launch {
            isLoginSuccessful = userRepository.validateUser(email, password)
            if (!isLoginSuccessful) {
                loginError = "Usuario o contraseña incorrectos"
            } else {
                loginError = null
            }
        }
    }
}
