package com.example.tecnisis.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tecnisis.R
import com.example.tecnisis.TecnisisScreen
import com.example.tecnisis.ui.components.InfoBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp)
    ) {
        InfoBox( description = stringResource(R.string.sign_up_info)) // Caja informativa
        Spacer(modifier = Modifier.height(8.dp))
        FormContent(navController, PaddingValues(10.dp))
    }
}


@Composable
fun FormContent(navController: NavHostController, padding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = padding.calculateTopPadding()),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        NameFields() // Campos Nombre y Apellidos
        EmailField() // Campo de Correo
        PasswordFields() // Campos de Contraseña
        DNIAndPhoneFields() // Campos DNI y Teléfono
        AddressField() // Campo de Dirección
        AccountLink(navController) // Enlace a "Ya tengo una cuenta"
    }
}

@Composable
fun NameFields() {
    val inputBackgroundColor = Color(0xFFFFEBEE)
    var nombre by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        TextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.weight(1f),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = inputBackgroundColor,
                unfocusedContainerColor = inputBackgroundColor
            )
        )
        TextField(
            value = apellidos,
            onValueChange = { apellidos = it },
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
fun EmailField() {
    val inputBackgroundColor = Color(0xFFFFEBEE)
    var correo by remember { mutableStateOf("") }

    TextField(
        value = correo,
        onValueChange = { correo = it },
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
fun PasswordFields() {
    val inputBackgroundColor = Color(0xFFFFEBEE)
    var contrasena by remember { mutableStateOf("") }
    var repetirContrasena by remember { mutableStateOf("") }

    TextField(
        value = contrasena,
        onValueChange = { contrasena = it },
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
        onValueChange = { repetirContrasena = it },
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
fun DNIAndPhoneFields() {
    val inputBackgroundColor = Color(0xFFFFEBEE)
    var dni by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        TextField(
            value = dni,
            onValueChange = { dni = it },
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
            onValueChange = { telefono = it },
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
fun AddressField() {
    val inputBackgroundColor = Color(0xFFFFEBEE)
    var direccion by remember { mutableStateOf("") }

    TextField(
        value = direccion,
        onValueChange = { direccion = it },
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
        TextButton(onClick = { navController.navigate(TecnisisScreen.Login.name)}) {
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
