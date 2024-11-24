package com.example.tecnisis.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tecnisis.config.datastore.DataStoreManager
import com.example.tecnisis.config.retrofit.TecnisisApi
import com.example.tecnisis.data.artwork.ArtworkRequest
import com.example.tecnisis.data.technique.TechniqueRequest
import com.example.tecnisis.ui.login.data.LoginRequest
import com.example.tecnisis.ui.login.data.LoginResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response

data class LoginUiState(
    val isLoading: Boolean = false,
    val isLoginSuccessful: Boolean = false,
    val email: String = "",
    val password: String = ""
)

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message


    fun emailUpdate(updatedEmail: String) {
        _uiState.value = _uiState.value.copy(email = updatedEmail)
    }

    fun passwordUpdate(updatedPassword: String) {
        _uiState.value = _uiState.value.copy(password = updatedPassword)
    }

    fun updateIsLoginSuccessful(value: Boolean) {
        _uiState.value = _uiState.value.copy(isLoginSuccessful = value)
    }

    fun resetMessage() {
        _message.value = ""
    }

    fun updateMessage(message: String) {
        _message.value = message
    }

    fun validateUser(dataStoreManager: DataStoreManager) {
        viewModelScope.launch {
            try {
                if (_uiState.value.email.isBlank() || _uiState.value.password.isBlank()) {
                    updateMessage("Por favor, complete todos los campos")
                    return@launch
                }
                val loginRequest = LoginRequest(_uiState.value.email, _uiState.value.password)
                val response = TecnisisApi.loginService.loginUser(loginRequest)
                if (response.isSuccessful) {
                    response.body()?.let {
                        dataStoreManager.saveId(it.id.toString())
                        dataStoreManager.saveRole(it.role)
                        updateIsLoginSuccessful(true)
                        updateMessage("Se ha iniciado sesión correctamente id:{${it.id}} role:{${it.role}}")
                    }
                } else {
                    val errorBody = JSONObject(response.errorBody()?.string()!!)
                    updateMessage(errorBody.getString("message"))
                    updateIsLoginSuccessful(false)
                }
            } catch (e: Exception) {
                updateMessage("Error de conexión: ${e.message}")
                updateIsLoginSuccessful(false)
            }
        }
    }
}
