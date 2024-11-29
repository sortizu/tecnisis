package com.example.tecnisis.ui.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tecnisis.config.datastore.DataStoreManager
import com.example.tecnisis.config.retrofit.TecnisisApi
import com.example.tecnisis.data.request.RequestResponse
import com.example.tecnisis.ui.components.convertMillisToDate

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class DashboardUiState(
    val isLoading: Boolean = false,
    val initalDate: String = "",
    val finalDate: String = "",
    val requests: List<RequestResponse> = emptyList(),
    val message: String = "",
    val role: String = ""
)

class DashboardViewModel(dataStoreManager: DataStoreManager): ViewModel() {
    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message
    private val _dataStoreManager: DataStoreManager = dataStoreManager

    init {
        var role = ""
        viewModelScope.launch {
            dataStoreManager.role.let {
                role = it.first()!!
            }
        }
        updateInitialDate(System.currentTimeMillis())
        updateFinalDate(System.currentTimeMillis())
        updateRole(role)
        loadAllRequests()
    }

    fun resetMessage() {
        //_uiState.value = _uiState.value.copy(message = "")
        _message.value = ""
    }

    fun updateRequests(requests: List<RequestResponse>) {
        _uiState.value = _uiState.value.copy(requests = requests)
    }

    fun updateIsLoading(isLoading: Boolean) {
        _uiState.value = _uiState.value.copy(isLoading = isLoading)
    }
    fun updateMessage(message: String) {
        _uiState.value = _uiState.value.copy(message = message)
    }
    fun updateRole(role: String) {
        _uiState.value = _uiState.value.copy(role = role)
    }
    fun updateInitialDate(date: Long) {
        _uiState.value = _uiState.value.copy(initalDate = convertMillisToDate(date))
    }
    fun updateFinalDate(date: Long) {
        _uiState.value = _uiState.value.copy(finalDate = convertMillisToDate(date))
    }

    // Updates the UI state with a new list of requests
    fun loadAllRequests(){
        viewModelScope.launch {
            try{
                var token = ""
                viewModelScope.launch {
                    _dataStoreManager.token.let {
                        token = it.first()!!
                    }
                }
                updateIsLoading(true)
                val response = TecnisisApi.requestService.getAllRequests(token)
                if (response.isSuccessful) {
                    response.body()?.let { requests ->
                        updateIsLoading(false)
                        updateRequests(requests)
                        resetMessage()
                    }
                } else {
                    updateIsLoading(false)
                    val errorBody = JSONObject(response.errorBody()?.string()!!)
                    updateMessage(errorBody.getString("message"))
                }
            }
            catch (e: Exception) {
                updateIsLoading(false)
                updateMessage("Error: ${e.message}")
            }
        }
    }

    fun getRequestsPerDay(): Map<String, Int> {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return _uiState.value.requests.groupingBy { request ->
            dateFormat.format(Date(request.date))
        }.eachCount()
    }

    fun getRequestBetweenDates(): List<RequestResponse> {
        val rangeDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val requestDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val initialDate = rangeDateFormat.parse(_uiState.value.initalDate)
        val finalDate = rangeDateFormat.parse(_uiState.value.finalDate)

        return _uiState.value.requests.filter { request ->
            val requestDate = requestDateFormat.parse(request.date) // Use requestDateFormat here
            requestDate?.after(initialDate)!! && requestDate?.before(finalDate)!! // Add null check and non-null assertion
        }
    }
}