package com.example.tecnisis.ui.sign_up

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tecnisis.config.retrofit.TecnisisApi
import com.example.tecnisis.ui.sign_up.data.SignUpRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

data class SignUpUiState(
    val isLoading: Boolean = false,
    val name: String = "",
    val surnames: String = "",
    val email: String = "",
    val pass: String = "",
    val repeatedPass: String = "",
    val dni: String = "",
    val phone: String = "",
    val address: String = "",
    val registrationSuccessful: Boolean = false
)

class SignUpViewModel () : ViewModel() {
    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()
    private val _message = MutableLiveData("")
    val message: LiveData<String> = _message

    fun updateName(newName: String) {
        _uiState.value = _uiState.value.copy(name = newName)
    }

    fun updateSurnames(newSurnames: String) {
        _uiState.value = _uiState.value.copy(surnames = newSurnames)
    }

    fun updateEmail(newEmail: String) {
        _uiState.value = _uiState.value.copy(email = newEmail)
    }

    fun updatePass(newPass: String) {
        _uiState.value = _uiState.value.copy(pass = newPass)
    }

    fun updateRepeatedPass(newRepeatedPass: String) {
        _uiState.value = _uiState.value.copy(repeatedPass = newRepeatedPass)
    }

    fun updateDNI(newDNI: String) {
        _uiState.value = _uiState.value.copy(dni = newDNI)
    }

    fun updatePhone(newPhone: String) {
        _uiState.value = _uiState.value.copy(phone = newPhone)
    }

    fun updateAddress(newAddress: String) {
        _uiState.value = _uiState.value.copy(address = newAddress)
    }

    fun updateRegistrationSuccessful(newRegistrationSuccessful: Boolean) {
        _uiState.value = _uiState.value.copy(registrationSuccessful = newRegistrationSuccessful)
    }

    fun resetMessage() {
        _message.value = ""
    }

    fun updateMessage(message: String) {
        _message.value = message
    }

    // Methods to handle input events

    fun registerUser() {
        //val context: Context = ContextAplication.applicationContext()
        viewModelScope.launch {
            try {
                val _name = _uiState.value.name
                val _surnames = _uiState.value.surnames
                val _email = _uiState.value.email
                val _pass = _uiState.value.pass
                val _repeatedPass = _uiState.value.repeatedPass
                val _dni = _uiState.value.dni
                val _phone = _uiState.value.phone
                val _address = _uiState.value.address

                if (_name.isBlank() || _surnames.isBlank() || _email.isBlank() ||
                    _pass.isBlank() || _repeatedPass.isBlank() || _dni.isBlank() ||
                    _phone.isBlank() || _address.isBlank()
                ) {
                    _message.value = "Por favor, complete todos los campos"
                } else if (_pass != _repeatedPass) {
                    _message.value = "Las contraseñas no coinciden"
                } else {
                    val signUpRequest = SignUpRequest(
                        email = _email,
                        password = _pass,
                        name = "$_name $_surnames",
                        idNumber = _dni,
                        address = _address,
                        gender = "M",
                        phone = _phone,
                        userRole = "ARTIST"
                    )
                    val response = TecnisisApi.signUpService.registerPerson(signUpRequest)
                    if (response.isSuccessful) {
                        response.body()?.let {
                            _message.value = "Registro exitoso"
                            updateRegistrationSuccessful(true)
                        }
                    } else {
                        val errorBody = JSONObject(response.errorBody()?.string()!!)
                        _message.value = "Error: " + errorBody.get("details").toString().replace(regex = "[\\[\\]]".toRegex(), replacement = "")
                    }
                }
            } catch (e: Exception) {
                _message.value = e.message
            }
        }

    }
}