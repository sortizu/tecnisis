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
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
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
import com.example.tecnisis.ui.components.BottomPattern
import com.example.tecnisis.ui.components.FloatingButton
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
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ){
            Text(
                text = "TECNISIS",
                style = TextStyle(fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color.Red)
            )
            Icon(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                tint = Color.Red,
                modifier = Modifier.size(32.dp)
            )
        }
        IconButton(onClick = { /* TODO: Open profile */ }) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Profile",
                tint = Color.Black,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Composable
fun TecnisisApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = TecnisisScreen.valueOf(
        backStackEntry?.destination?.route ?: TecnisisScreen.ListRequests.name
    )
    val context = LocalContext.current

    Scaffold(
        topBar = { TopAppBar() },
        modifier = Modifier
            .padding(top = 16.dp),
        floatingActionButton = {FloatingButton(onClick = { /*TODO*/ }, icon = Icons.Default.Add)}
    ) { innerPadding ->
        //val uiState by viewModel.uiState.collectAsState()
        NavHost(
            navController = navController,
            startDestination = TecnisisScreen.ListRequests.name,
            modifier = Modifier
                .padding(innerPadding)
                .background(color = Color.Transparent)
        ) {
            composable(route = TecnisisScreen.ListRequests.name) {
                ScreenTitle(text = context.getString(currentScreen.title))
                ListRequestsScreen(viewModel = viewModel())
                // This box will be placed at the bottom of the screen amd show a image that will fill all the horizontal space, this image will also have an absolute position and other other (as a floatin button) can be used in front of it
                //FloatingButton(onClick = { /*TODO*/ }, icon = Icons.Default.Add)
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