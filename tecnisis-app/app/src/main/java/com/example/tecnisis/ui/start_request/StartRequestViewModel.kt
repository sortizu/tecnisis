package com.example.tecnisis.ui.start_request

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tecnisis.config.retrofit.TecnisisApi
import com.example.tecnisis.data.artwork.ArtworkRequest
import com.example.tecnisis.data.request.CreateRequest
import com.example.tecnisis.data.technique.TechniqueResponse
import com.example.tecnisis.ui.components.convertMillisToDate
import com.example.tecnisis.ui.login.data.LoginRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

data class StartRequestUiState(
    val artworkTitle: String = "",
    val image: String = "",
    val height: Double = 0.0,
    val width: Double = 0.0,
    val date: String = "",
    val techniqueIndex: Int = 0,
    val creationSuccessful: Boolean = false,
    val techniques: List<TechniqueResponse> = emptyList(),
    val message: String = "",
    val isDialogVisible: Boolean = false
)

class StartRequestViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(StartRequestUiState())
    val uiState: StateFlow<StartRequestUiState> = _uiState.asStateFlow()

    init {
        loadTechniques()
        // Sets the current day using the format dd/MM/yyyy
        updateDate(System.currentTimeMillis())
    }

    fun updateArtworkTitle(newTitle: String) {
        _uiState.value = _uiState.value.copy(artworkTitle = newTitle)
    }
    fun updateHeight(newHeight: Double) {
        _uiState.value = _uiState.value.copy(height = newHeight)
    }
    fun updateWidth(newWidth: Double) {
        _uiState.value = _uiState.value.copy(width = newWidth)
    }
    fun updateDate(newDateInMillis: Long) {
        _uiState.value = _uiState.value.copy(date = convertMillisToDate(newDateInMillis))
    }
    fun updateTechniqueId(newTechniqueIndex: Int) {
        _uiState.value = _uiState.value.copy(techniqueIndex = newTechniqueIndex)
    }

    fun updateIsDialogVisible(newIsDialogVisible: Boolean) {
        //_isDialogVisible.value = newIsDialogVisible
        _uiState.value = _uiState.value.copy(isDialogVisible = newIsDialogVisible)
    }

    fun updateImage(newImage: String) {
        _uiState.value = _uiState.value.copy(image = newImage)
    }

    fun updateTechniques(newTechniques: List<TechniqueResponse>) {
        _uiState.value = _uiState.value.copy(techniques = newTechniques)
    }

    fun updateCreationSuccessful(newCreationSuccessful: Boolean) {
        _uiState.value = _uiState.value.copy(creationSuccessful = newCreationSuccessful)
    }

    fun resetMessage() {
        _uiState.value = _uiState.value.copy(message = "")
        //_message.value = ""
    }

    fun updateMessage(message: String) {
        //_message.value = message
        _uiState.value = _uiState.value.copy(message = message)
    }

    fun loadTechniques() {
        viewModelScope.launch {
            try {
                val response = TecnisisApi.techniquesService.getTechniques()
                if (response.isSuccessful) {
                    response.body()?.let {
                        updateTechniques(it)
                    }
                } else {
                    val errorBody = JSONObject(response.errorBody()?.string()!!)
                    updateMessage(errorBody.getString("error"))
                }
            } catch (e: Exception) {
                    //_message.value = e.message
                e.message?.let { updateMessage(it) }
                }
        }
    }

    fun startRequest() {
        viewModelScope.launch {
            try {
                val _artworkTitle = _uiState.value.artworkTitle
                val _image = _uiState.value.image
                val _height = _uiState.value.height
                val _width = _uiState.value.width
                val _date = _uiState.value.date
                val _techniqueId = _uiState.value.techniques[_uiState.value.techniqueIndex].id

                // Obtain the artist ID
                val _artistId = 3L

                if (_image.isEmpty() || _artworkTitle.isBlank() || _height == 0.0 || _width == 0.0 || _date.isBlank() || _techniqueId == -1L) {
                    updateMessage("Por favor, completa todos los campos")
                    return@launch
                }
                val artworkRequest = ArtworkRequest(
                    title = _artworkTitle,
                    image = _image,
                    creationDate = _date,
                    height =  _height,
                    width = _width,
                    artistId = _artistId,
                    techniqueId = _techniqueId
                )
                // Save artwork
                val artworkResponse = TecnisisApi.artworkService.saveArtwork(artworkRequest)
                var _artworkId = -1L
                if (artworkResponse.isSuccessful) {
                    artworkResponse.body()?.let {
                        _artworkId = it.id
                    }
                }else{
                    updateMessage("Error al guardar la obra de arte")
                    return@launch
                }
                val request = CreateRequest(
                    "Pending",
                    _artworkId
                )
                val _requestResponse = TecnisisApi.requestService.createRequest(request)
                if (_requestResponse.isSuccessful) {
                    _requestResponse.body()?.let {
                        updateMessage("Solicitud enviada con éxito")
                        updateCreationSuccessful(true)
                    }
                } else {
                    val errorBody = JSONObject(_requestResponse.errorBody()?.string()!!)
                    updateMessage(errorBody.get("details").toString())
                }
            } catch (e: Exception) {
                e.message?.let { updateMessage(it) }
            }
        }
    }
}