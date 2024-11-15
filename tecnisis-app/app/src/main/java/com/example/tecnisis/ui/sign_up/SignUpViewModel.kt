package com.example.tecnisis.ui.sign_up

import android.content.Context
import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tecnisis.config.navigation.ContextAplication
import com.example.tecnisis.config.retrofit.TecnisisApi

import com.example.tecnisis.data.ui_states.SignUpUiState
import com.example.tecnisis.ui.sign_up.data.SignUpRequest
import com.example.tecnisis.ui.sign_up.data.SignUpService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

class SignUpViewModel () : ViewModel() {



    // Variables to store user input

    private val _name = MutableLiveData("")
    val name: LiveData<String> = _name
    private val _surnames = MutableLiveData("")
    val surnames: LiveData<String> = _surnames
    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email
    private val _pass = MutableLiveData("")
    val pass: LiveData<String> = _pass
    private val _repeatedPass = MutableLiveData("")
    val repeatedPass: LiveData<String> = _repeatedPass
    private val _dni = MutableLiveData("")
    val dni: LiveData<String> = _dni
    private val _phone = MutableLiveData("")
    val phone: LiveData<String> = _phone
    private val _address = MutableLiveData("")
    val address: LiveData<String> = _address


    private val _signUpSuccess = MutableLiveData(false)
    val signUpSuccess: LiveData<Boolean> = _signUpSuccess
    private val _message = MutableLiveData("")
    val message: LiveData<String> = _message

    init {

    }

    // Methods to handle value changes

    fun updateName(newName: String) {
        _name.value = newName
    }

    fun updateSurnames(newSurnames: String) {
        _surnames.value = newSurnames
    }

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun updatePass(newPass: String) {
        _pass.value = newPass
    }

    fun updateRepeatedPass(newRepeatedPass: String) {
        _repeatedPass.value = newRepeatedPass
    }

    fun updateDNI(newDNI: String) {
        _dni.value = newDNI
    }

    fun updatePhone(newPhone: String) {
        _phone.value = newPhone
    }

    fun updateAddress(newAddress: String) {
        _address.value = newAddress
    }

    // Methods to handle input events

    fun registerUser() {
        //val context: Context = ContextAplication.applicationContext()
        viewModelScope.launch {
            try {
                if (_name.value!!.isBlank() || _surnames.value!!.isBlank() || _email.value!!.isBlank() ||
                    _pass.value!!.isBlank() || _repeatedPass.value!!.isBlank() || _dni.value!!.isBlank() ||
                    _phone.value!!.isBlank() || _address.value!!.isBlank()
                ) {
                    _message.value = "Por favor, complete todos los campos"
                } else if (_pass.value != _repeatedPass.value) {
                    _message.value = "Las contraseñas no coinciden"
                } else {
                    val signUpRequest = SignUpRequest(
                        email = _email.value!!,
                        password = _pass.value!!,
                        name = _name.value!! + " " + _surnames.value!!,
                        idNumber = _dni.value!!,
                        address = _address.value!!,
                        gender = "X",
                        phone = _phone.value!!,
                        userRole = "USER"
                    )
                    val response = TecnisisApi.signUpService.registerPerson(signUpRequest)
                    if (response.isSuccessful) {
                        response.body()?.let {
                            //_message.value = it.message
                            Log.i("Response", it.toString())
                        }
                    } else {
                        val errorBody = JSONObject(response.errorBody()?.string()!!)
                        _message.value = errorBody.getString("error")
                    }
                }
            } catch (e: Exception) {
                _message.value = e.message
            }
        }

    }

    fun clearMessage() {
        _message.value = ""
    }

}