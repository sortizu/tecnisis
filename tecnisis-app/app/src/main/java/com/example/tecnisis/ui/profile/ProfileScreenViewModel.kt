package com.example.tecnisis.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tecnisis.config.datastore.DataStoreManager
import com.example.tecnisis.config.retrofit.TecnisisApi
import com.example.tecnisis.data.person.PersonRequest
import com.example.tecnisis.data.person.PersonResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class ProfileScreenUiState(
    val isLoading: Boolean = false,
    val idPerson: Long = -1L,
    val name: String = "",
    val dni: String = "",
    val phone: String = "",
    val address: String = "",
    val role: String = ""
)

class ProfileScreenViewModel(idPerson: Long): ViewModel() {
    private val _uiState = MutableStateFlow(ProfileScreenUiState())
    val uiState: StateFlow<ProfileScreenUiState> = _uiState.asStateFlow()
    private val _message = MutableLiveData("")
    val message: LiveData<String> = _message

    init {
        loadProfile(idPerson)
    }

    fun updateIdPerson(newIdPerson: Long) {
        _uiState.value = _uiState.value.copy(idPerson = newIdPerson)
    }
    fun updateName(newName: String) {
        _uiState.value = _uiState.value.copy(name = newName)
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
    fun updateRole(newRole: String) {
        _uiState.value = _uiState.value.copy(role = newRole)
    }
    fun resetMessage() {
        _message.value = ""
    }
    fun updateMessage(message: String) {
        _message.value = message
    }

    fun loadProfile(idPerson: Long) {
        viewModelScope.launch {
            updateIdPerson(idPerson)
            val response = TecnisisApi.personService.getPerson(idPerson)
            if (response.isSuccessful) {
                response.body()?.let { person ->
                    updateName(person.name)
                    updateDNI(person.dni)
                    updatePhone(person.phone)
                    updateAddress(person.address)
                    when (person.role) {
                        "ARTIST" -> updateRole("Artista")
                        "ART-EVALUATOR" -> updateRole("Evaluador artístico")
                        "ECONOMIC-EVALUATOR" -> updateRole("Evaluador económico")
                        "MANAGER" -> updateRole("Administrador")
                    }
                }
            }
        }
    }

    fun updateProfile() {
        viewModelScope.launch {
            val request = PersonRequest(
                name = _uiState.value.name,
                dni = _uiState.value.dni,
                phone = _uiState.value.phone,
                address = _uiState.value.address,
                role = _uiState.value.role,
                gender = "X"
            )
            val response = TecnisisApi.personService.updatePerson(request, _uiState.value.idPerson)
            if (response.isSuccessful) {
                updateMessage("Perfil actualizado correctamente")
            }
            else {
                updateMessage("Error al actualizar el perfil")
            }
        }
    }
}