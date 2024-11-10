package com.example.tecnisis.ui.sign_up

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.tecnisis.R
import com.example.tecnisis.ui.AppViewModelProvider
import com.example.tecnisis.ui.components.CustomBasicTextField
import com.example.tecnisis.ui.components.CustomEmailField
import com.example.tecnisis.ui.components.CustomNumberField
import com.example.tecnisis.ui.components.CustomPasswordField
import com.example.tecnisis.ui.components.CustomPhoneNumberField
import com.example.tecnisis.ui.components.InfoBox
import com.example.tecnisis.ui.list_artist_requests.ListArtistRequestsViewModel
import kotlinx.coroutines.coroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel,
    onSignUp: MutableState<()->Unit>,
    navController: NavHostController
) {

    val name by viewModel.name.observeAsState("")
    val surnames by viewModel.surnames.observeAsState("")
    val email by viewModel.email.observeAsState("")
    val pass by viewModel.pass.observeAsState("")
    val repeatedPass by viewModel.repeatedPass.observeAsState("")
    val dni by viewModel.dni.observeAsState("")
    val phone by viewModel.phone.observeAsState("")
    val address by viewModel.address.observeAsState("")
    val message by viewModel.message.observeAsState("")

    onSignUp.value = {
        viewModel.registerUser()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        InfoBox(description = stringResource(R.string.sign_up_info))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)){
            CustomBasicTextField(stringResource(R.string.name), name, Modifier.weight(1f), onValueChange = { viewModel.updateName(it) })
            CustomBasicTextField(stringResource(R.string.surnames), surnames, Modifier.weight(1f), onValueChange = { viewModel.updateSurnames(it) })
        }
        CustomEmailField(stringResource(R.string.email), email , Modifier.fillMaxWidth(), onValueChange = { viewModel.updateEmail(it) })
        CustomPasswordField(stringResource(R.string.password), pass, Modifier.fillMaxWidth(), onValueChange = { viewModel.updatePass(it) })
        CustomPasswordField(stringResource(R.string.repeat_pass), repeatedPass, Modifier.fillMaxWidth(), onValueChange = { viewModel.updateRepeatedPass(it) })
        Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            CustomNumberField(stringResource(R.string.dni), dni, Modifier.weight(1f), onValueChange = { viewModel.updateDNI(it) })
            CustomPhoneNumberField(stringResource(R.string.phone), phone, Modifier.weight(1f), onValueChange = { viewModel.updatePhone(it) })
        }
        CustomBasicTextField(stringResource(R.string.address), address, Modifier.fillMaxWidth(), onValueChange = { viewModel.updateAddress(it) })

    }
}
/*
@Composable
fun FormContent(
    nombre: String,
    onNombreChange: (String) -> Unit,
    apellidos: String,
    onApellidosChange: (String) -> Unit,
    correo: String,
    onCorreoChange: (String) -> Unit,
    contrasena: String,
    onContrasenaChange: (String) -> Unit,
    repetirContrasena: String,
    onRepetirContrasenaChange: (String) -> Unit,
    dni: String,
    onDniChange: (String) -> Unit,
    telefono: String,
    onTelefonoChange: (String) -> Unit,
    direccion: String,
    onDireccionChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        NameFields(nombre = nombre, onNombreChange = onNombreChange, apellidos = apellidos, onApellidosChange = onApellidosChange)
        EmailField(correo = correo, onCorreoChange = onCorreoChange)
        PasswordFields(contrasena = contrasena, onContrasenaChange = onContrasenaChange, repetirContrasena = repetirContrasena, onRepetirContrasenaChange = onRepetirContrasenaChange)
        DNIAndPhoneFields(dni = dni, onDniChange = onDniChange, telefono = telefono, onTelefonoChange = onTelefonoChange)
        AddressField(direccion = direccion, onDireccionChange = onDireccionChange)
    }
}

@Composable
fun NameFields(nombre: String, onNombreChange: (String) -> Unit,
               apellidos: String, onApellidosChange: (String) -> Unit) {
    val inputBackgroundColor = Color(0xFFFFEBEE)
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        TextField(
            value = nombre,
            onValueChange = onNombreChange,
            label = { Text("Nombre") },
            modifier = Modifier.weight(1f),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = inputBackgroundColor,
                unfocusedContainerColor = inputBackgroundColor
            )
        )
        TextField(
            value = apellidos,
            onValueChange = onApellidosChange,
            label = { Text("Apellidos") },
            modifier = Modifier.weight(1f),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = inputBackgroundColor,
                unfocusedContainerColor = inputBackgroundColor
            )
        )
    }
}

@Composable
fun PasswordFields(
    contrasena: String, onContrasenaChange: (String) -> Unit,
    repetirContrasena: String, onRepetirContrasenaChange: (String) -> Unit
) {
    val inputBackgroundColor = Color(0xFFFFEBEE)
    TextField(
        value = contrasena,
        onValueChange = onContrasenaChange,
        label = { Text("Contraseña") },
        visualTransformation = PasswordVisualTransformation(),
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = inputBackgroundColor,
            unfocusedContainerColor = inputBackgroundColor
        )
    )
    TextField(
        value = repetirContrasena,
        onValueChange = onRepetirContrasenaChange,
        label = { Text("Repetir contraseña") },
        visualTransformation = PasswordVisualTransformation(),
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = inputBackgroundColor,
            unfocusedContainerColor = inputBackgroundColor
        )
    )
}

@Composable
fun DNIAndPhoneFields(dni: String, onDniChange: (String) -> Unit,
                      telefono: String, onTelefonoChange: (String) -> Unit) {
    val inputBackgroundColor = Color(0xFFFFEBEE)
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        TextField(
            value = dni,
            onValueChange = onDniChange,
            label = { Text("DNI") },
            modifier = Modifier.weight(1f),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = inputBackgroundColor,
                unfocusedContainerColor = inputBackgroundColor
            )
        )
        TextField(
            value = telefono,
            onValueChange = onTelefonoChange,
            label = { Text("Teléfono") },
            modifier = Modifier.weight(1f),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = inputBackgroundColor,
                unfocusedContainerColor = inputBackgroundColor
            )
        )
    }
}

@Composable
fun AddressField(direccion: String, onDireccionChange: (String) -> Unit) {
    val inputBackgroundColor = Color(0xFFFFEBEE)
    TextField(
        value = direccion,
        onValueChange = onDireccionChange,
        label = { Text("Dirección") },
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = inputBackgroundColor,
            unfocusedContainerColor = inputBackgroundColor
        )
    )
}

@Composable
fun AccountLink(navController: NavHostController) {
    Spacer(modifier = Modifier.height(0.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        TextButton(onClick = { navController.navigate(TecnisisScreen.Login.name) }) {
            Text(
                text = "Ya tengo una cuenta",
                style = androidx.compose.ui.text.TextStyle(
                    textDecoration = androidx.compose.ui.text.style.TextDecoration.Underline
                ),
                color = Color.Gray
            )
        }
    }
}
*/