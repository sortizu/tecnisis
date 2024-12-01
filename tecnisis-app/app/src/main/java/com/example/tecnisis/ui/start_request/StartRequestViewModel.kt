package com.example.tecnisis.ui.start_request

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tecnisis.config.datastore.DataStoreManager
import com.example.tecnisis.config.retrofit.TecnisisApi
import com.example.tecnisis.data.artwork.ArtworkRequest
import com.example.tecnisis.data.request.CreateRequest
import com.example.tecnisis.data.technique.TechniqueResponse
import com.example.tecnisis.ui.components.convertMillisToDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
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

class StartRequestViewModel(dataStoreManager: DataStoreManager): ViewModel() {
    private val _uiState = MutableStateFlow(StartRequestUiState())
    val uiState: StateFlow<StartRequestUiState> = _uiState.asStateFlow()
    private val _dataStoreManager = dataStoreManager

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

                if (_image.isEmpty() || _artworkTitle.isBlank() || _height == 0.0 || _width == 0.0 || _date.isBlank() || _techniqueId == -1L) {
                    updateMessage("Por favor, completa todos los campos")
                    return@launch
                }

                var token = ""
                _dataStoreManager.token.let{
                    token = it.first()!!
                }
                var artistId = -1L
                _dataStoreManager.idRole.let{
                    artistId = it.first()!!.toLong()
                }

                val artworkRequest = ArtworkRequest(
                    title = _artworkTitle,
                    image = _image,
                    creationDate = _date,
                    height =  _height,
                    width = _width,
                    artistId = artistId,
                    techniqueId = _techniqueId
                )
                // Save artwork
                val artworkResponse = TecnisisApi.artworkService.saveArtwork("Bearer $token", artworkRequest)
                var _artworkId = -1L
                if (artworkResponse.isSuccessful) {
                    artworkResponse.body()?.let {
                        _artworkId = it.id
                    }
                }else{

                    val errorBody = JSONObject(artworkResponse.errorBody()?.string()!!)
                    Log.d("error", errorBody.getString("error"))
                    return@launch
                }
                val request = CreateRequest(
                    "Pending",
                    _artworkId
                )
                val _requestResponse = TecnisisApi.requestService.createRequest("Bearer $token",request)
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