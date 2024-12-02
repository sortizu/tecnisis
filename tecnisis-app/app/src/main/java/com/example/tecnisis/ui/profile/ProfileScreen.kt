package com.example.tecnisis.ui.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tecnisis.R
import com.example.tecnisis.TecnisisScreen
import com.example.tecnisis.ui.components.BottomPattern
import com.example.tecnisis.ui.components.CustomBasicTextField
import com.example.tecnisis.ui.components.CustomFloatingButton
import com.example.tecnisis.ui.components.CustomNumberField
import com.example.tecnisis.ui.components.CustomPhoneNumberField
import com.example.tecnisis.ui.components.LabelItem
import com.example.tecnisis.ui.components.ScreenTitle
import com.example.tecnisis.ui.components.TecnisisTopAppBar
import com.example.tecnisis.ui.components.TopBarState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreen(
    currentScreen: TecnisisScreen = TecnisisScreen.Profile,
    viewModel: ProfileScreenViewModel,
    modifier: Modifier = Modifier,
    navController: NavController,
    floatingButton: MutableState<@Composable () -> Unit>,
    topAppBar: MutableState<@Composable () -> Unit>,
    editable : Boolean = false
){
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    topAppBar.value = {
        TecnisisTopAppBar(
            state = TopBarState.COLLAPSED
        )
    }

    floatingButton.value = {
        if (editable){
            CustomFloatingButton(
                onClick = { viewModel.updateProfile() },
                icon = Icons.Default.Save
            )
        }
    }

    Scaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .background(color = Color.Transparent),
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                ScreenTitle(text = context.getString(currentScreen.title))
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.Transparent),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Profile",
                        modifier = Modifier.size(128.dp)
                    )
                    LabelItem(
                        text = uiState.role,
                        icon = Icons.Default.Info
                    )
                }
                CustomBasicTextField(
                    stringResource(R.string.name),
                    uiState.name,
                    Modifier.fillMaxWidth(),
                    onValueChange = { viewModel.updateName(it) },
                    editable = editable
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CustomNumberField(
                        stringResource(R.string.dni),
                        uiState.dni,
                        Modifier.weight(1f),
                        onValueChange = { viewModel.updateDNI(it) },
                        editable = editable
                    )
                    CustomPhoneNumberField(
                        stringResource(R.string.phone),
                        uiState.phone,
                        Modifier.weight(1f),
                        onValueChange = { viewModel.updatePhone(it) },
                        editable = editable
                    )
                }
                CustomBasicTextField(
                    stringResource(R.string.address),
                    uiState.address,
                    Modifier.fillMaxWidth(),
                    onValueChange = { viewModel.updateAddress(it) },
                    editable = editable
                )
            }
            BottomPattern()
        }
    }


}