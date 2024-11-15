package com.example.tecnisis.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tecnisis.config.datastore.DataStoreManager
import com.example.tecnisis.config.retrofit.TecnisisApi
import com.example.tecnisis.ui.login.data.LoginRequest
import com.example.tecnisis.ui.login.data.LoginResponse
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response

class LoginViewModel : ViewModel() {

    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email

    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password

    private val _loginError = MutableLiveData<String?>(null)
    val loginError: LiveData<String?> = _loginError

    private val _isLoginSuccessful = MutableLiveData(false) // Modificado: ahora es MutableLiveData
    val isLoginSuccessful: LiveData<Boolean> = _isLoginSuccessful

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
            try {
                if (_email.value.isNullOrBlank() || _password.value.isNullOrBlank()) {
                    _loginError.value = "Por favor, completa todos los campos"
                    _isLoginSuccessful.value = false
                    return@launch
                }

                val loginRequest = LoginRequest(_email.value!!, _password.value!!)
                val response = TecnisisApi.loginService.loginUser(loginRequest)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _loginError.value = null // Limpiar el mensaje de error en caso de éxito
                        _isLoginSuccessful.value = true // Indicar que el login fue exitoso
                        dataStoreManager.saveId(it.id.toString())
                        //dataStoreManager.saveRole(it.role)
                    }
                } else {
                    val errorBody = JSONObject(response.errorBody()?.string()!!)
                    _loginError.value = errorBody.getString("error")
                    _isLoginSuccessful.value = false
                }
            } catch (e: Exception) {
                _loginError.value = e.message
                _isLoginSuccessful.value = false
            }
        }
    }

    // Limpiar mensaje de error después de mostrarlo
    fun clearLoginError() {
        _loginError.value = null
    }
}
