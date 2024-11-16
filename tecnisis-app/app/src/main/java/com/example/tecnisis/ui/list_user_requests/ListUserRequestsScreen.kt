package com.example.tecnisis.ui.list_user_requests

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tecnisis.TecnisisScreen
import com.example.tecnisis.config.datastore.DataStoreManager
import com.example.tecnisis.ui.components.CustomSingleChoiceSegmentedButton
import com.example.tecnisis.ui.components.RequestCard
import com.example.tecnisis.ui.components.ScreenTitle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListUserRequestsScreen(
    viewModel: ListUserRequestsViewModel,
    currentScreen: TecnisisScreen = TecnisisScreen.ListRequests,
    modifier: Modifier = Modifier,
    navController: NavController,
    enableFloatingActionButton: MutableState<Boolean>,
    floatingButtonPressed: MutableState<() -> Unit>,
) {
    // Collect the UI state from the ViewModel
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val dataStoreManager = remember { DataStoreManager(context) }

    val role by viewModel.role.collectAsState()
    viewModel.loadRole(dataStoreManager)
    viewModel.loadArtistRequests(dataStoreManager)



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp)
            .background(color = Color.Transparent)
    ) {
        // Search bar at the top
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

        if (role == "specialist") {
            // hiding the floating action button
            enableFloatingActionButton.value = false
            // Showing the request filter
            CustomSingleChoiceSegmentedButton(
                options = listOf("Pendientes", "Aprobadas", "Rechazadas"),
                onSelectionChanged = { }
            )
        } else {
            enableFloatingActionButton.value = true
            floatingButtonPressed.value =
                { navController.navigate(TecnisisScreen.StartRequest.name) }
        }

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
                val requests = uiState.requests
                LazyColumn {
                    items(requests.size) { index ->
                        RequestCard(index + 1, requests[index], onCardClick = {
                            if (role == "specialist") {
                                navController.navigate(TecnisisScreen.ArtisticRequestReview.name)
                            } else {
                                navController.navigate(TecnisisScreen.ViewRequest.name)
                            }
                        }
                        )
                    }
                }
            }
        }
        // Bottom Pattern Background

    }
}

@Preview(showBackground = true)
@Composable
fun ListRequestsScreenPreview() {

}