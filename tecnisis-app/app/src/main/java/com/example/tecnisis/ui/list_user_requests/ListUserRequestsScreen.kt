package com.example.tecnisis.ui.list_user_requests

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tecnisis.TecnisisScreen
import com.example.tecnisis.ui.components.BottomPattern
import com.example.tecnisis.ui.components.CustomFloatingButton
import com.example.tecnisis.ui.components.CustomSingleChoiceSegmentedButton
import com.example.tecnisis.ui.components.RequestCard
import com.example.tecnisis.ui.components.ScreenTitle
import com.example.tecnisis.ui.components.TecnisisTopAppBar
import com.example.tecnisis.ui.components.TopBarState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListUserRequestsScreen(
    viewModel: ListUserRequestsViewModel,
    currentScreen: TecnisisScreen = TecnisisScreen.ListRequests,
    modifier: Modifier = Modifier,
    navController: NavController,
    floatingButton: MutableState<@Composable () -> Unit>,
    topAppBar: MutableState<@Composable () -> Unit>
) {
    // Collect the UI state from the ViewModel
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val requests = viewModel.getRequestsByStatusFilter(viewModel.getRequestsBySearchFilter(uiState.requests, uiState.searchFilter), uiState.statusFilter)

    topAppBar.value = {
        TecnisisTopAppBar(
            state = TopBarState.EXPANDED,
            onLogoutClick = {
                navController.navigate(TecnisisScreen.Login.name)
            },
            onProfileClick = {
                navController.navigate(TecnisisScreen.Profile.name)
            }
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Transparent)
    ) {
        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
        ) {
            // Search bar at the top
            ScreenTitle(text = context.getString(currentScreen.title))
            Spacer(modifier = Modifier.height(8.dp))
            SearchBar(
                query = uiState.searchFilter,
                onQueryChange = { viewModel.updateSearchFilter(it) },
                onSearch = {},
                active = false,
                onActiveChange = { /* Handle focus changes if needed */ },
                placeholder = { Text("Buscar solicitudes") },
                content = {}
            )

            Spacer(modifier = Modifier.height(8.dp))

            when (uiState.role) {
                "ARTIST" -> { floatingButton.value = { CustomFloatingButton(onClick = { navController.navigate(TecnisisScreen.StartRequest.name) }, icon = Icons.Default.Add) } }
                "ART-EVALUATOR" -> {
                    floatingButton.value = {}
                    CustomSingleChoiceSegmentedButton(
                        options = listOf("Pendientes", "Aprobadas", "Rechazadas"),
                        onSelectionChanged = {
                            when (it){
                                0 -> { viewModel.updateStatusFilter("PENDING") }
                                1 -> { viewModel.updateStatusFilter("APPROVED") }
                                2 -> { viewModel.updateStatusFilter("REJECTED") }
                            }
                        }
                    )
                }
                else -> {
                    floatingButton.value = {}
                    CustomSingleChoiceSegmentedButton(
                        options = listOf("Pendientes", "Revisadas"),
                        onSelectionChanged = {
                            when (it){
                                0 -> { viewModel.updateStatusFilter("PENDING") }
                                1 -> { viewModel.updateStatusFilter("APPROVED") }
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }

                requests == null || requests.isEmpty() -> {
                    Text(
                        text = "No se encontraron solicitudes",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }

                else -> {
                    // Display the list of requests if data is loaded successfully
                    LazyColumn (
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ){
                        items(requests.size) { index ->
                            RequestCard(index + 1, requests[index], onCardClick = {
                                when (uiState.role) {
                                    "ART-EVALUATOR" -> {
                                        navController.navigate(TecnisisScreen.ArtisticRequestEvaluation.name + "/${requests[index].id}")
                                    }
                                    "ECONOMIC-EVALUATOR" -> {
                                        navController.navigate(TecnisisScreen.EconomicRequestEvaluation.name + "/${requests[index].id}")
                                    }
                                    else -> {
                                        navController.navigate(TecnisisScreen.ViewRequest.name + "/${requests[index].id}")
                                    }
                                }
                            }
                            )
                        }
                    }
                }
            }
            // Bottom Pattern Background
            Text(
                text = uiState.message
            )

        }
        Spacer(modifier = Modifier.height(8.dp))
        BottomPattern()
    }

}

@Preview(showBackground = true)
@Composable
fun ListRequestsScreenPreview() {

}