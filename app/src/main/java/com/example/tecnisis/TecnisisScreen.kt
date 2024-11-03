package com.example.tecnisis

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tecnisis.ui.ListRequestsScreen
import com.example.tecnisis.ui.LoginScreen
import com.example.tecnisis.ui.components.BottomPattern
import com.example.tecnisis.ui.components.FloatingButton
import com.example.tecnisis.ui.components.ScreenTitle
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import com.example.tecnisis.ui.SignUpScreen
import com.example.tecnisis.ui.StartRequestScreen

enum class TecnisisScreen(@StringRes val title: Int) {
    Login(title = R.string.iniciar_sesion),
    SignUp(title = R.string.crear_cuenta),
    ListRequests(title = R.string.lista_de_solicitudes),
    StartRequest(title = R.string.iniciar_solicitud)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TecnisisTopAppBar(
    onProfileClick: () -> Unit
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
        modifier = Modifier.padding(0.dp)
    )
}


@Composable
fun TecnisisApp() {
    val navController = rememberNavController()
    // val currentRoute = navBackStackEntry?.destination?.route
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = TecnisisScreen.valueOf(
        backStackEntry?.destination?.route ?: TecnisisScreen.ListRequests.name
    )
    val context = LocalContext.current

    Scaffold(
        topBar = {
            // Mostrar TopAppBar solo si no estamos en la pantalla de Login
            if (currentScreen != TecnisisScreen.Login) {
                TecnisisTopAppBar({})
            }
        },
        modifier = Modifier
            .padding(top = 0.dp),
        floatingActionButton = {
            when (currentScreen) {
                TecnisisScreen.Login -> {
                    FloatingButton(
                        icon = Icons.AutoMirrored.Filled.ArrowForward,
                        onClick = { navController.navigate(TecnisisScreen.ListRequests.name) }
                    )
                }
                TecnisisScreen.SignUp -> {
                    FloatingButton(
                        icon = Icons.Default.ArrowForward,
                        onClick = { navController.navigate(TecnisisScreen.ListRequests.name) }
                    )
                }
                TecnisisScreen.ListRequests -> {
                    FloatingButton(
                        icon = Icons.Default.Add,
                        onClick = { navController.navigate(TecnisisScreen.StartRequest.name) }
                    )
                }
                TecnisisScreen.StartRequest -> {
                    FloatingButton(
                        icon = Icons.Default.Save,
                        onClick = { navController.navigate(TecnisisScreen.ListRequests.name) }
                    )
                }
                else -> {}
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = TecnisisScreen.Login.name,
            modifier = Modifier
                .padding(innerPadding)
                .background(color = Color.Transparent)
        ) {
            // Pantalla de Login
            composable(route = TecnisisScreen.Login.name) {
                LoginScreen(
                    onLogin = { navController.navigate(TecnisisScreen.ListRequests.name) },
                    onSignUp = { navController.navigate(TecnisisScreen.SignUp.name) }
                )
            }
            // Pantalla de Crear Cuenta
            composable(route = TecnisisScreen.SignUp.name) {
                SignUpScreen(
                    navController = navController
                )
            }
            // Pantalla de Lista de Solicitudes
            composable(route = TecnisisScreen.ListRequests.name) {
                ListRequestsScreen()
            }
            composable(route = TecnisisScreen.StartRequest.name) {
                StartRequestScreen()
            }
        }
        BottomPattern()
    }
}

@Preview(showBackground = true)
@Composable
fun TecnisisAppPreview() {
    TecnisisApp()
}
