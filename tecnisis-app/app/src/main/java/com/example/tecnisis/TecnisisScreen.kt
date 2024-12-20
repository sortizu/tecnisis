package com.example.tecnisis

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.example.tecnisis.ui.components.BottomPattern
import com.example.tecnisis.ui.components.CustomFloatingButton
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TecnisisTopAppBar(
    onProfileClick: () -> Unit,
    onLogoutClick: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "TECNISIS",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    tint = Color.Red,
                    modifier = Modifier.size(32.dp)
                )
            }
        },
        actions = {
            IconButton(onClick = onProfileClick) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Profile",
                    tint = Color.Black,
                    modifier = Modifier.size(32.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = onLogoutClick)
            {
                Icon(
                    imageVector = Icons.Default.Logout,
                    contentDescription = "Logo",
                    tint = Color.Black,
                    modifier = Modifier.size(32.dp)
                )
            }

        },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Transparent),
    )
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
    val enableFloatingActionButton = remember { mutableStateOf(true) }
    val floatingButtonPressed = remember { mutableStateOf({}) }
    val errorMessage = remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            if (currentScreen != TecnisisScreen.Login) {
                TecnisisTopAppBar(
                    onProfileClick = { navController.navigate(TecnisisScreen.Profile.name) },
                    onLogoutClick = { navController.navigate(TecnisisScreen.Login.name) })
            }
        },
        floatingActionButton =
        {
            if (enableFloatingActionButton.value){
                val icon = when (currentScreen) {
                    TecnisisScreen.Login -> Icons.AutoMirrored.Filled.ArrowForward
                    TecnisisScreen.SignUp -> Icons.Default.ArrowForward
                    TecnisisScreen.ListRequests -> Icons.Default.Add
                    TecnisisScreen.StartRequest -> Icons.Default.Save
                    TecnisisScreen.ArtisticRequestEvaluation -> Icons.Default.Save
                    TecnisisScreen.EconomicRequestEvaluation -> Icons.Default.Save
                    TecnisisScreen.Profile -> Icons.Default.Save
                    TecnisisScreen.ViewRequest -> Icons.Default.Save
                    TecnisisScreen.Dashboard -> Icons.Default.Save
                }
                CustomFloatingButton(
                    onClick = floatingButtonPressed.value,
                    icon = icon,
                    modifier = Modifier.size(75.dp)
                )
            }
        },
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
                enableFloatingActionButton.value = true
                LoginScreen(
                    viewModel = viewModel(modelClass = LoginViewModel::class.java),
                    navController = navController,
                    onLogin = floatingButtonPressed,
                    snackbarHostState = snackbarHostState
                )
            }
            composable(route = TecnisisScreen.SignUp.name) {
                SignUpScreen(
                    viewModel = viewModel(modelClass = SignUpViewModel::class.java),
                    navController = navController,
                    onSignUp = floatingButtonPressed,
                    snackbarHostState = snackbarHostState
                )
            }
            composable(route = TecnisisScreen.ListRequests.name) {
                ListUserRequestsScreen(
                    viewModel = ListUserRequestsViewModel(dataStoreManager),
                    enableFloatingActionButton = enableFloatingActionButton,
                    navController = navController,
                    floatingButtonPressed = floatingButtonPressed
                )
            }
            composable(route = TecnisisScreen.StartRequest.name) {
                StartRequestScreen(
                    viewModel = viewModel(modelClass = StartRequestViewModel::class.java),
                    navController = navController,
                    snackbarHostState = snackbarHostState,
                    onStartRequest = floatingButtonPressed
                )
            }
            composable(route = TecnisisScreen.ArtisticRequestEvaluation.name) {
                val requestId = it.arguments?.getString("requestId")?.toLong()
                ArtisticRequestReviewScreen(
                    viewModel = ArtisticRequestEvaluationViewModel(requestId!!,dataStoreManager),
                    navController = navController,
                    floatingButtonPressed = floatingButtonPressed
                )
            }
            composable(route = TecnisisScreen.EconomicRequestEvaluation.name) {
                val requestId = it.arguments?.getString("requestId")?.toLong()
                EconomicRequestEvaluationScreen(
                    viewModel = EconomicRequestEvaluationViewModel(requestId!!,dataStoreManager),
                    navController = navController
                )
            }
            composable(route = TecnisisScreen.ViewRequest.name + "/{requestId}") {
                enableFloatingActionButton.value = false
                val requestId = it.arguments?.getString("requestId")?.toLong()
                ViewRequestScreen(
                    viewModel = ViewRequestViewModel(requestId!!),
                    snackbarHostState = snackbarHostState
                )
            }
            composable(route = TecnisisScreen.Profile.name + "/{idPerson}") {
                val idPerson = it.arguments?.getString("idPerson")?.toLong()
                ProfileScreen(
                    viewModel = ProfileScreenViewModel(idPerson!!),
                    navController = navController,
                    floatingButtonPressed = floatingButtonPressed
                )
            }
            composable(route = TecnisisScreen.Dashboard.name) {
                enableFloatingActionButton.value = false
                DashboardScreen(
                    viewModel = DashboardViewModel(dataStoreManager),
                    navController = navController,
                    floatingButtonPressed = floatingButtonPressed,
                    enableFloatingActionButton = enableFloatingActionButton
                )
            }
        }
        BottomPattern()
    }
}


@Preview(showBackground = true)
@Composable
fun TecnisisAppPreview() {

}
