package com.example.tecnisis.ui.sign_up

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tecnisis.R
import com.example.tecnisis.TecnisisScreen
import com.example.tecnisis.ui.components.BottomPattern
import com.example.tecnisis.ui.components.CustomBasicTextField
import com.example.tecnisis.ui.components.CustomEmailField
import com.example.tecnisis.ui.components.CustomFloatingButton
import com.example.tecnisis.ui.components.CustomNumberField
import com.example.tecnisis.ui.components.CustomPasswordField
import com.example.tecnisis.ui.components.CustomPhoneNumberField
import com.example.tecnisis.ui.components.InfoBox
import com.example.tecnisis.ui.components.TecnisisTopAppBar
import com.example.tecnisis.ui.components.TopBarState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel,
    floatingButton: MutableState<@Composable () -> Unit>,
    topAppBar: MutableState<@Composable () -> Unit>,
    navController: NavHostController,
    snackbarHostState: SnackbarHostState
) {

    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val message by viewModel.message.observeAsState()

    topAppBar.value = {
        TecnisisTopAppBar(
            state = TopBarState.COLLAPSED
        )
    }

    floatingButton.value = {
        CustomFloatingButton(
            onClick = { viewModel.registerUser() },
            icon = Icons.Default.ArrowForward
        )
    }

    LaunchedEffect(uiState.registrationSuccessful) {
        if (uiState.registrationSuccessful) {
            // Waits half a second before navigating to the ListRequests screen
            delay(100)
            navController.navigate(TecnisisScreen.Login.name)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        InfoBox(description = stringResource(R.string.sign_up_info))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)){
            CustomBasicTextField(stringResource(R.string.name), uiState.name, Modifier.weight(1f), onValueChange = { viewModel.updateName(it) })
            CustomBasicTextField(stringResource(R.string.surnames), uiState.surnames, Modifier.weight(1f), onValueChange = { viewModel.updateSurnames(it) })
        }
        CustomEmailField(stringResource(R.string.email), uiState.email , Modifier.fillMaxWidth(), onValueChange = { viewModel.updateEmail(it) })
        CustomPasswordField(stringResource(R.string.password), uiState.pass, Modifier.fillMaxWidth(), onValueChange = { viewModel.updatePass(it) })
        CustomPasswordField(stringResource(R.string.repeat_pass), uiState.repeatedPass, Modifier.fillMaxWidth(), onValueChange = { viewModel.updateRepeatedPass(it) })
        Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            CustomNumberField(stringResource(R.string.dni), uiState.dni, Modifier.weight(1f), onValueChange = { viewModel.updateDNI(it) })
            CustomPhoneNumberField(stringResource(R.string.phone), uiState.phone, Modifier.weight(1f), onValueChange = { viewModel.updatePhone(it) })
        }
        CustomBasicTextField(stringResource(R.string.address), uiState.address, Modifier.fillMaxWidth(), onValueChange = { viewModel.updateAddress(it) })
        BottomPattern()
        message?.let { msg ->
            if (msg.isNotEmpty()) {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(msg)
                    viewModel.resetMessage()
                }
            }
        }
    }
}