package com.example.tecnisis.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.tecnisis.R
import com.example.tecnisis.TecnisisScreen
import com.example.tecnisis.data.UserRepository
import com.example.tecnisis.data.Persona
import com.example.tecnisis.data.Usuario
import com.example.tecnisis.ui.components.InfoBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    navController: NavHostController,
    userRepository: UserRepository,
    onSignUp: (Usuario, Persona) -> Unit,
    nombre: MutableState<String>,
    apellidos: MutableState<String>,
    correo: MutableState<String>,
    contrasena: MutableState<String>,
    repetirContrasena: MutableState<String>,
    dni: MutableState<String>,
    telefono: MutableState<String>,
    direccion: MutableState<String>,
    errorMessage: MutableState<String>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp)
    ) {
        InfoBox(description = stringResource(R.string.sign_up_info))
        Spacer(modifier = Modifier.height(8.dp))

        FormContent(
            nombre = nombre.value,
            onNombreChange = { nombre.value = it },
            apellidos = apellidos.value,
            onApellidosChange = { apellidos.value = it },
            correo = correo.value,
            onCorreoChange = { correo.value = it },
            contrasena = contrasena.value,
            onContrasenaChange = { contrasena.value = it },
            repetirContrasena = repetirContrasena.value,
            onRepetirContrasenaChange = { repetirContrasena.value = it },
            dni = dni.value,
            onDniChange = { dni.value = it },
            telefono = telefono.value,
            onTelefonoChange = { telefono.value = it },
            direccion = direccion.value,
            onDireccionChange = { direccion.value = it }
        )

        AccountLink(navController)

        if (errorMessage.value.isNotEmpty()) {
            Text(
                text = errorMessage.value,
                color = Color.Red,
                modifier = Modifier.padding(16.dp),
                fontSize = 14.sp
            )
        }
    }
}

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
fun EmailField(correo: String, onCorreoChange: (String) -> Unit) {
    val inputBackgroundColor = Color(0xFFFFEBEE)
    TextField(
        value = correo,
        onValueChange = onCorreoChange,
        label = { Text("Correo") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = inputBackgroundColor,
            unfocusedContainerColor = inputBackgroundColor
        )
    )
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
