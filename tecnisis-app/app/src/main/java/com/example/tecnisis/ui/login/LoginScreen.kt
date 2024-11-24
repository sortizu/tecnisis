package com.example.tecnisis.ui.login

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.tecnisis.R
import com.example.tecnisis.TecnisisScreen
import com.example.tecnisis.config.datastore.DataStoreManager
import com.example.tecnisis.ui.components.CustomEmailField
import com.example.tecnisis.ui.components.CustomPasswordField
import com.example.tecnisis.ui.components.InfoBox
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLogin: MutableState<() -> Unit>,
    navController: NavHostController,
    snackbarHostState: SnackbarHostState
) {

    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val dataStoreManager = remember { DataStoreManager(context) }
    val message by viewModel.message.observeAsState("")

    onLogin.value = {
        viewModel.validateUser(dataStoreManager)
    }

    LaunchedEffect(uiState.isLoginSuccessful) {
        if (uiState.isLoginSuccessful) {
            // Waits half a second before navigating to the ListRequests screen
            delay(500)
            navController.navigate(TecnisisScreen.ListRequests.name) {
                popUpTo(TecnisisScreen.Login.name) { inclusive = true }
            }
        }
    }
    Scaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.backgroundlogin),
                contentDescription = stringResource(id = R.string.login_title),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(175.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Red)
                    .padding(vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.login_title),
                        style = TextStyle(
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        ),
                        textAlign = TextAlign.Center
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.logo),
                        tint = Color.White,
                        contentDescription = stringResource(id = R.string.login_title),
                        modifier = Modifier.size(32.dp)
                    )
                }
                Text(
                    text = stringResource(id = R.string.login_subtitle),
                    style = TextStyle(fontSize = 14.sp, color = Color.White),
                    textAlign = TextAlign.Center
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                InfoBox(description = stringResource(id = R.string.login_instructions))
                CustomEmailField(
                    label = stringResource(id = R.string.email),
                    value = uiState.email,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = { viewModel.emailUpdate(it) }
                )
                CustomPasswordField(
                    label = stringResource(id = R.string.password),
                    value = uiState.password,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = { viewModel.passwordUpdate(it) }
                )
                Text(
                    text = stringResource(id = R.string.no_account),
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.Gray,
                        textDecoration = TextDecoration.Underline
                    ),
                    modifier = Modifier.clickable { navController.navigate(TecnisisScreen.SignUp.name) }
                )
            }
        }
    }

    LaunchedEffect(message) {
        if (message.isNotEmpty()){
            snackbarHostState.showSnackbar(message)
            viewModel.resetMessage()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
}
