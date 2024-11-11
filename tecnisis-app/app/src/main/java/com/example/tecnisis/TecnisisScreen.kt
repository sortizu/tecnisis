package com.example.tecnisis

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tecnisis.data.user.UserRepository
import com.example.tecnisis.ui.list_artist_requests.ListRequestsScreen
import com.example.tecnisis.ui.login.LoginScreen
import com.example.tecnisis.ui.sign_up.SignUpScreen
import com.example.tecnisis.ui.start_request.StartRequestScreen
import com.example.tecnisis.ui.components.BottomPattern
import com.example.tecnisis.ui.list_artist_requests.ListArtistRequestsViewModel
import com.example.tecnisis.ui.login.LoginViewModel
import com.example.tecnisis.ui.sign_up.SignUpViewModel
import kotlinx.coroutines.CoroutineScope

enum class TecnisisScreen(@StringRes val title: Int) {
    Login(title = R.string.iniciar_sesion),
    SignUp(title = R.string.crear_cuenta),
    ListRequests(title = R.string.lista_de_solicitudes),
    StartRequest(title = R.string.iniciar_solicitud)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TecnisisTopAppBar(onProfileClick: () -> Unit) {
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
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Transparent),
    )
}

@Composable
fun TecnisisApp(userRepository: UserRepository) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = TecnisisScreen.valueOf(
        backStackEntry?.destination?.route ?: TecnisisScreen.ListRequests.name
    )

    val userId = remember { mutableStateOf(0) }
    val floatingButtonPressed = remember { mutableStateOf({}) }
    val errorMessage = remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            if (currentScreen != TecnisisScreen.Login) {
                TecnisisTopAppBar(onProfileClick = {})
            }
        },
        floatingActionButton = {
            TecnisisFloatingActionButton(
                currentScreen = currentScreen,
                snackbarHostState = snackbarHostState,
                onFloatingButtonClick = floatingButtonPressed,
                errorMessage = errorMessage
            )
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
                LoginScreen(
                    viewModel = viewModel(modelClass = LoginViewModel::class.java),
                    navController = navController,
                    onLogin = floatingButtonPressed,

                )
            }
            composable(route = TecnisisScreen.SignUp.name) {
                SignUpScreen(
                    viewModel = viewModel(modelClass = SignUpViewModel::class.java),
                    navController = navController,
                    onSignUp = floatingButtonPressed
                )
            }
            composable(route = TecnisisScreen.ListRequests.name) {
                ListRequestsScreen(
                    viewModel = viewModel(modelClass = ListArtistRequestsViewModel::class.java),
                )
            }
            composable(route = TecnisisScreen.StartRequest.name) {
                StartRequestScreen()
            }
        }
        BottomPattern()
    }
}

@Composable
fun TecnisisFloatingActionButton(
    currentScreen: TecnisisScreen,
    snackbarHostState: SnackbarHostState,
    onFloatingButtonClick: MutableState<() -> Unit>,
    errorMessage: MutableState<String>
) {
    val icon = when (currentScreen) {
        TecnisisScreen.Login -> Icons.AutoMirrored.Filled.ArrowForward
        TecnisisScreen.SignUp -> Icons.Default.ArrowForward
        TecnisisScreen.ListRequests -> Icons.Default.Add
        TecnisisScreen.StartRequest -> Icons.Default.Save
    }
    FloatingActionButton(
        onClick = onFloatingButtonClick.value,
        containerColor = Color.Red,
        modifier = Modifier.size(60.dp)
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = Color.White)
    }
    if (errorMessage.value.isNotEmpty()) {
        LaunchedEffect(errorMessage.value) {
            snackbarHostState.showSnackbar(errorMessage.value)
            errorMessage.value = ""
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TecnisisAppPreview() {

}
