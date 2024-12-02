package com.example.tecnisis.ui.list_user_requests

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tecnisis.config.datastore.DataStoreManager
import com.example.tecnisis.config.retrofit.TecnisisApi
import com.example.tecnisis.data.request.RequestResponse

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.json.JSONObject

data class ListUserRequestUiState(
    val isLoading: Boolean = false,
    val requests: List<RequestResponse> = emptyList(),
    val searchFilter: String = "",
    val statusFilter: String = "PENDING",
    val message: String = "",
    val role: String = ""
)

class ListUserRequestsViewModel(dataStoreManager: DataStoreManager): ViewModel() {
    private val _uiState = MutableStateFlow(ListUserRequestUiState())
    val uiState: StateFlow<ListUserRequestUiState> = _uiState.asStateFlow()
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message
    val dataStoreManager: DataStoreManager = dataStoreManager
    var statusList: MutableList<String> = mutableListOf()
    init {
        loadRequests()
    }

    fun updateIsLoading(isLoading: Boolean) {
        _uiState.value = _uiState.value.copy(isLoading = isLoading)
    }

    fun updateRequests(requests: List<RequestResponse>) {
        _uiState.value = _uiState.value.copy(requests = requests)
    }

    fun resetMessage() {
        //_uiState.value = _uiState.value.copy(message = "")
        _message.value = ""
    }

    fun updateMessage(message: String) {
        _uiState.value = _uiState.value.copy(message = message)
    }
    fun updateRole(role: String) {
        _uiState.value = _uiState.value.copy(role = role)
    }
    fun updateSearchFilter(filter: String) {
        _uiState.value = _uiState.value.copy(searchFilter = filter)
    }

    fun updateStatusFilter(filter: String) {
        _uiState.value = _uiState.value.copy(statusFilter = filter)
    }

    fun getRequestsBySearchFilter(requests: List<RequestResponse>,query: String) : List<RequestResponse> {
        val filteredRequests = requests.filter { request ->
            request.artWork.title.contains(query, ignoreCase = true)
        }
        return filteredRequests
    }
    fun getRequestsByStatusFilter(requests: List<RequestResponse>,query: String) : List<RequestResponse> {
        val filteredRequests = requests.filter { request ->
            request.status.contains(query, ignoreCase = true)
        }
        return filteredRequests
    }

    // Updates the UI state with a new list of requests
    fun loadRequests(){
        viewModelScope.launch {
            try {
                var id= -1L
                dataStoreManager.idRole.let {
                    id = it.first()!!.toLong()
                }
                var role = ""
                dataStoreManager.role.let {
                    role = it.first()!!
                }
                updateRole(role)
                updateIsLoading(true)
                val token = dataStoreManager.token.first() ?: ""
                var requests = emptyList<RequestResponse>()
                val response = when (_uiState.value.role) {
                    "ARTIST" -> {
                        TecnisisApi.artistService.getArtistRequests("Bearer $token",id)
                    }

                    "ART-EVALUATOR" -> {
                        TecnisisApi.specialistService.getArtisticRequests("Bearer $token",id)
                    }

                    else -> {
                        TecnisisApi.specialistService.getEconomicRequests("Bearer $token",id)
                    }
                }
                if (response.isSuccessful) {
                    response.body()?.let {
                        requests = it
                    }
                } else {
                    updateIsLoading(false)
                    val errorBody = JSONObject(response.errorBody()?.string()!!)
                    updateMessage(errorBody.getString("message"))
                    return@launch
                }
                for (request in requests){

                    when (role) {
                        "ART-EVALUATOR" -> {

                            val artisticResponse = TecnisisApi.evaluationService.getArtisticEvaluationByRequest("Bearer $token",request.id)
                            if (artisticResponse.isSuccessful) {
                                artisticResponse.body()?.let { artisticEvaluation ->
                                    request.status = artisticEvaluation.status
                                    Log.d("ArtisticEvaluation", "Status: ${artisticEvaluation.status}")
                                }
                            }
                        }
                        "ECONOMIC-EVALUATOR" -> {
                            val economicResponse = TecnisisApi.evaluationService.getEconomicEvaluationByRequest("Bearer $token",request.id)
                            if (economicResponse.isSuccessful) {
                                economicResponse.body()?.let { economicEvaluation ->
                                    request.status = economicEvaluation.status
                                }
                            }
                        }
                    }
                }
                updateRequests(requests)
                updateIsLoading(false)
            }
            catch (e: Exception) {
                updateIsLoading(false)
                updateMessage("Error: ${e.message}")
            }
        }
    }

}