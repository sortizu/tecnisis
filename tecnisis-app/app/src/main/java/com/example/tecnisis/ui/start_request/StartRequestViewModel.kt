package com.example.tecnisis.ui.start_request

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tecnisis.config.retrofit.TecnisisApi
import com.example.tecnisis.data.artwork.ArtworkRequest
import com.example.tecnisis.data.technique.TechniqueResponse
import com.example.tecnisis.ui.login.data.LoginRequest
import kotlinx.coroutines.launch
import org.json.JSONObject

class StartRequestViewModel: ViewModel() {
    private val _artworkTitle = MutableLiveData("")
    val artworkTitle: LiveData<String> = _artworkTitle
    private val _image = MutableLiveData("")
    val image: LiveData<String> = _image
    private val _height = MutableLiveData(0.0)
    val height: LiveData<Double> = _height
    private val _width = MutableLiveData(0.0)
    val width: LiveData<Double> = _width
    private val _date = MutableLiveData("")
    val date: LiveData<String> = _date
    private val _techniqueId = MutableLiveData(-1L)
    val techniqueId : LiveData<Long> = _techniqueId
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message
    private val _techniqueResponses = MutableLiveData<List<TechniqueResponse>>()
    val techniqueResponses: LiveData<List<TechniqueResponse>> = _techniqueResponses
    private val _isDialogVisible = MutableLiveData(false)
    val isDialogVisible: LiveData<Boolean> = _isDialogVisible

    fun updateArtworkTitle(newTitle: String) {
        _artworkTitle.value = newTitle
    }
    fun updateHeight(newHeight: Double) {
        _height.value = newHeight
    }
    fun updateWidth(newWidth: Double) {
        _width.value = newWidth
    }
    fun updateDate(newDate: String) {
        _date.value = newDate
    }
    fun updateTechniqueId(newTechniqueId: Long) {
        _techniqueId.value = newTechniqueId
    }
    fun updateImage(newImage: String) {
        _image.value = newImage
    }
    fun updateIsDialogVisible(newIsDialogVisible: Boolean) {
        _isDialogVisible.value = newIsDialogVisible
    }

    fun loadTechniques() {
        viewModelScope.launch {
            try {
                val response = TecnisisApi.TechniquesService.getTechniques()

                if (response.isSuccessful) {
                    response.body()?.let {
                        _techniqueResponses.value = it
                    }
                } else {
                    val errorBody = JSONObject(response.errorBody()?.string()!!)
                    _message.value = errorBody.getString("error")
                }
            } catch (e: Exception) {
                    _message.value = e.message
                }
        }
    }

    fun startRequest() {
        viewModelScope.launch {
            try {
                if (_artworkTitle.value.isNullOrBlank() || _height.value == 0.0 || _width.value == 0.0 || _date.value.isNullOrBlank() || _techniqueId.value == -1L) {
                    _message.value = "Por favor, completa todos los campos"
                    return@launch
                }
                val artworkRequest = ArtworkRequest(
                    _artworkTitle.value!!,
                    _date.value!!,
                    _image.value!!,
                    _height.value!!,
                    _width.value!!,
                    _techniqueId.value!!,
                )
                val response = TecnisisApi.startRequestService.startRequest(artworkRequest)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _message.value = it
                    }
                } else {
                    val errorBody = JSONObject(response.errorBody()?.string()!!)
                    _message.value = errorBody.getString("error")
                }
            } catch (e: Exception) {
                _message.value = e.message
            }
        }
    }
}