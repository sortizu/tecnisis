package com.example.tecnisis.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tecnisis.R
import com.example.tecnisis.TecnisisScreen
import com.example.tecnisis.ui.components.RequestList
import com.example.tecnisis.ui.components.ScreenTitle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListRequestsScreen(
    viewModel: ListArtistRequestsViewModel = viewModel(factory = AppViewModelProvider.Factory),
    currentScreen: TecnisisScreen = TecnisisScreen.ListRequests,
    modifier: Modifier = Modifier
) {
    // Collect the UI state from the ViewModel
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp)
            .background(color = Color.Transparent)
    ) {
        // Search bar at the top
        val context = LocalContext.current
        var searchQuery by remember { mutableStateOf("") }
        ScreenTitle(text = context.getString(currentScreen.title))
        Spacer(modifier = Modifier.height(8.dp))
        SearchBar(
            query = searchQuery,
            onQueryChange = { searchQuery = it },
            onSearch = {
                //viewModel.filterRequests(searchQuery)  // Optional: Implement search/filter in ViewModel
            },
            active = false,
            onActiveChange = { /* Handle focus changes if needed */ },
            placeholder = { Text("Buscar solicitudes") },
            content = {}
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar la lista de solicitudes o un indicador de carga
        when {
            uiState.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            uiState.errorMessage != null -> {
                Text(
                    text = uiState.errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            else -> {
                // Display the list of requests if data is loaded successfully
                RequestList(
                    solicitudes = uiState.solicitudesWithObras
                )
            }
        }
        // Bottom Pattern Background

    }
}

@Preview(showBackground = true)
@Composable
fun ListRequestsScreenPreview() {
    ListRequestsScreen()
}