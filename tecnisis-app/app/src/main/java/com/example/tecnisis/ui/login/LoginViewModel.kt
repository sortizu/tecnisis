package com.example.tecnisis.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tecnisis.config.datastore.DataStoreManager
import com.example.tecnisis.config.retrofit.TecnisisApi
import com.example.tecnisis.data.user.UserRepository
import com.example.tecnisis.ui.login.data.LoginRequest
import com.example.tecnisis.ui.login.data.LoginResponse
import kotlinx.coroutines.launch
import org.json.JSONObject

class LoginViewModel() : ViewModel() {

    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email

    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password

    private val _loginError = MutableLiveData<String?>(null)
    val loginError: LiveData<String?> = _loginError

    private var isLoginSuccessful = false

    // Función para actualizar el email
    fun emailUpdate(updatedEmail: String) {
        _email.value = updatedEmail
    }

    // Función para actualizar el password
    fun passwordUpdate(updatedPassword: String) {
        _password.value = updatedPassword
    }

    // Función para validar el usuario con el repositorio
    fun validateUser(dataStoreManager: DataStoreManager) {
        viewModelScope.launch {
            try{
                if (_email.value.isNullOrBlank() || _password.value.isNullOrBlank()) {
                    _loginError.value = "Por favor, completa todos los campos"
                    return@launch
                }
                val loginRequest = LoginRequest(_email.value!!, _password.value!!)
                val response = TecnisisApi.loginService.loginUser(loginRequest)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _loginError.value = it.message
                        dataStoreManager.saveId(it.userId.toString())
                        dataStoreManager.saveRole(it.role)
                    }
                } else {
                    val errorBody = JSONObject(response.errorBody()?.string()!!)
                    _loginError.value = errorBody.getString("error")
                }
            }
            catch (e: Exception){
                _loginError.value = e.message
            }
        }
    }
}
