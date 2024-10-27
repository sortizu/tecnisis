package com.example.tecnisis

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
import com.example.tecnisis.ui.theme.TecnisisTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TecnisisTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "form_screen") {
        composable("form_screen") { FormScreen(navController) }
        composable("empty_screen") { EmptyScreen() }
        composable("login_screen") { EmptyScreen() }
    }
}

@Composable
fun EmptyScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Pantalla Vacía")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "TECNISIS") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("empty_screen") }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("empty_screen") },
                containerColor = Color(0xFFFFC1CC), // Color rosado pastel
                modifier = Modifier.size(80.dp)
            ) {
                Icon(
                    Icons.Filled.Check,
                    contentDescription = "Confirm",
                    tint = Color.Red,
                    modifier = Modifier.size(45.dp)
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            InfoBox() // Caja informativa
            Spacer(modifier = Modifier.height(8.dp))
            FormContent(navController, PaddingValues(10.dp))
        }
    }
}

@Composable
fun InfoBox() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .background(
                color = Color(0xFFFFA726),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
            )
            .padding(vertical = 2.dp, horizontal = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Llene sus datos personales en cada página del formulario para finalizar su registro.",
            color = Color.Black,
            fontSize = 14.sp
        )
    }
}

@Composable
fun FormContent(navController: NavHostController, padding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = padding.calculateTopPadding())
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
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
        TextButton(onClick = { navController.navigate("login_screen") }) {
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

@Preview
@Composable
fun Preview() {
    TecnisisTheme {
        MyApp()
    }
}
