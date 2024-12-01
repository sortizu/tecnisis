package com.example.tecnisis

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tecnisis.config.datastore.DataStoreManager
import com.example.tecnisis.ui.artistic_request_evaluation.ArtisticRequestEvaluationViewModel
import com.example.tecnisis.ui.artistic_request_evaluation.ArtisticRequestReviewScreen
import com.example.tecnisis.ui.list_user_requests.ListUserRequestsScreen
import com.example.tecnisis.ui.login.LoginScreen
import com.example.tecnisis.ui.sign_up.SignUpScreen
import com.example.tecnisis.ui.start_request.StartRequestScreen
import com.example.tecnisis.ui.components.TecnisisTopAppBar
import com.example.tecnisis.ui.dashboard.DashboardScreen
import com.example.tecnisis.ui.dashboard.DashboardViewModel
import com.example.tecnisis.ui.economic_request_evaluation.EconomicRequestEvaluationScreen
import com.example.tecnisis.ui.economic_request_evaluation.EconomicRequestEvaluationViewModel
import com.example.tecnisis.ui.list_user_requests.ListUserRequestsViewModel
import com.example.tecnisis.ui.login.LoginViewModel
import com.example.tecnisis.ui.profile.ProfileScreen
import com.example.tecnisis.ui.profile.ProfileScreenViewModel
import com.example.tecnisis.ui.sign_up.SignUpViewModel
import com.example.tecnisis.ui.start_request.StartRequestViewModel
import com.example.tecnisis.ui.view_request.ViewRequestScreen
import com.example.tecnisis.ui.view_request.ViewRequestViewModel

enum class TecnisisScreen(@StringRes val title: Int) {
    Login(title = R.string.iniciar_sesion),
    SignUp(title = R.string.crear_cuenta),
    ListRequests(title = R.string.lista_de_solicitudes),
    StartRequest(title = R.string.iniciar_solicitud),
    ArtisticRequestEvaluation(title = R.string.artistic_request_evaluation),
    EconomicRequestEvaluation(title = R.string.economic_request_evaluation),
    ViewRequest(title = R.string.view_request),
    Profile(title = R.string.profile),
    Dashboard(title = R.string.dashboard)
}

@Composable
fun TecnisisApp() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val backStackEntry by navController.currentBackStackEntryAsState()
    var currentRoute = backStackEntry?.destination?.route
    if (currentRoute != null){
        val splittedStr = currentRoute.split("/")
        if (splittedStr.size > 1){
            currentRoute = splittedStr[0]
        }
    }else{
        currentRoute = TecnisisScreen.Login.name
    }
    val currentScreen = TecnisisScreen.valueOf(currentRoute)
    val dataStoreManager = remember { DataStoreManager(context) }
    // Composable floating button as a state variable
    val floatingButton = remember { mutableStateOf<@Composable ()-> Unit>({})}
    val topAppBar = remember { mutableStateOf<@Composable ()-> Unit>({})}
    val enableFloatingActionButton = remember { mutableStateOf(true) }
    val floatingButtonPressed = remember { mutableStateOf({}) }
    val errorMessage = remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = topAppBar.value,
        floatingActionButton = floatingButton.value,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = TecnisisScreen.Login.name,
            modifier = Modifier
                .padding(innerPadding)
                .background(color = Color.Transparent)
        ) {
            composable(route = TecnisisScreen.Login.name) {
                LoginScreen(
                    viewModel = viewModel(modelClass = LoginViewModel::class.java),
                    navController = navController,
                    floatingButton = floatingButton,
                    snackbarHostState = snackbarHostState,
                    topAppBar = topAppBar
                )
            }
            composable(route = TecnisisScreen.SignUp.name) {
                SignUpScreen(
                    viewModel = viewModel(modelClass = SignUpViewModel::class.java),
                    navController = navController,
                    floatingButton = floatingButton,
                    snackbarHostState = snackbarHostState,
                    topAppBar = topAppBar
                )
            }
            composable(route = TecnisisScreen.ListRequests.name) {
                ListUserRequestsScreen(
                    viewModel = ListUserRequestsViewModel(dataStoreManager),
                    floatingButton = floatingButton,
                    navController = navController,
                    topAppBar = topAppBar
                )
            }
            composable(route = TecnisisScreen.StartRequest.name) {
                StartRequestScreen(
                    viewModel = StartRequestViewModel(dataStoreManager),
                    navController = navController,
                    snackbarHostState = snackbarHostState,
                    floatingButton = floatingButton,
                    topAppBar = topAppBar
                )
            }
            composable(route = TecnisisScreen.ArtisticRequestEvaluation.name) {
                val requestId = it.arguments?.getString("requestId")?.toLong()
                ArtisticRequestReviewScreen(
                    viewModel = ArtisticRequestEvaluationViewModel(requestId!!,dataStoreManager),
                    navController = navController,
                    floatingButton = floatingButton,
                    topAppBar = topAppBar
                )
            }
            composable(route = TecnisisScreen.EconomicRequestEvaluation.name) {
                val requestId = it.arguments?.getString("requestId")?.toLong()
                EconomicRequestEvaluationScreen(
                    viewModel = EconomicRequestEvaluationViewModel(requestId!!,dataStoreManager),
                    navController = navController,
                    floatingButton = floatingButton,
                    topAppBar = topAppBar
                )
            }
            composable(route = TecnisisScreen.ViewRequest.name + "/{requestId}") {
                enableFloatingActionButton.value = false
                val requestId = it.arguments?.getString("requestId")?.toLong()
                ViewRequestScreen(
                    viewModel = ViewRequestViewModel(requestId!!, dataStoreManager),
                    snackbarHostState = snackbarHostState,
                    floatingButton = floatingButton,
                    topAppBar = topAppBar
                )
            }
            composable(route = TecnisisScreen.Profile.name) {
                ProfileScreen(
                    viewModel = ProfileScreenViewModel(dataStoreManager),
                    navController = navController,
                    floatingButton = floatingButton,
                    topAppBar = topAppBar
                )
            }
            composable(route = TecnisisScreen.Dashboard.name) {
                enableFloatingActionButton.value = false
                DashboardScreen(
                    viewModel = DashboardViewModel(dataStoreManager),
                    navController = navController,
                    floatingButton = floatingButton,
                    topAppBar = topAppBar
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TecnisisAppPreview() {

}
