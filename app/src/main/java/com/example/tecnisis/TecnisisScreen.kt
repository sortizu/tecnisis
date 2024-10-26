package com.example.tecnisis

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tecnisis.ui.ListRequestsScreen
import com.example.tecnisis.ui.components.ScreenTitle

enum class TecnisisScreen(@StringRes val title: Int) {
    Login(title = R.string.iniciar_sesion),
    SignUp(title = R.string.crear_cuenta),
    ListRequests(title = R.string.lista_de_solicitudes),
    AddRequest(title = R.string.iniciar_solicitud)
}

@Composable
fun TopAppBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "TECNISIS",
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Red)
        )
        IconButton(onClick = { /* TODO: Open profile */ }) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Profile",
                tint = Color.Black
            )
        }
    }
}

@Composable
fun TecnisisApp() {
    val navController = rememberNavController()
    Scaffold(
        topBar = { TopAppBar() }
    ) { innerPadding ->
        //val uiState by viewModel.uiState.collectAsState()
        NavHost(
            navController = navController,
            startDestination = TecnisisScreen.Login.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            /*
            composable(route = TecnisisScreen.Login.name) {
            }
            composable(route = TecnisisScreen.SignUp.name) {
            }
            */
            composable(route = TecnisisScreen.ListRequests.name) {
                ScreenTitle(text = TecnisisScreen.ListRequests.name)
                ListRequestsScreen(viewModel = viewModel())
            }
            /*
            composable(route = TecnisisScreen.AddRequest.name) {

            }*/
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TecnisisAppPreview() {
    TecnisisApp()
}