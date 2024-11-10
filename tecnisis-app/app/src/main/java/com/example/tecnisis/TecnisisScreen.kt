package com.example.tecnisis

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tecnisis.data.user.UserRepository
import com.example.tecnisis.ui.list_artist_requests.ListRequestsScreen
import com.example.tecnisis.ui.login.LoginScreen
import com.example.tecnisis.ui.sign_up.SignUpScreen
import com.example.tecnisis.ui.start_request.StartRequestScreen
import com.example.tecnisis.ui.components.BottomPattern
import com.example.tecnisis.ui.sign_up.SignUpViewModel
import kotlinx.coroutines.CoroutineScope

enum class TecnisisScreen(@StringRes val title: Int) {
    Login(title = R.string.iniciar_sesion),
    SignUp(title = R.string.crear_cuenta),
    ListRequests(title = R.string.lista_de_solicitudes),
    StartRequest(title = R.string.iniciar_solicitud)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TecnisisTopAppBar(onProfileClick: () -> Unit) {
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
                Spacer(modifier = Modifier.width(8.dp))
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
    )
}

@Composable
fun TecnisisApp(userRepository: UserRepository) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = TecnisisScreen.valueOf(
        backStackEntry?.destination?.route ?: TecnisisScreen.ListRequests.name
    )

    val floatingButtonPressed = remember { mutableStateOf({}) }

    val nombre = remember { mutableStateOf("") }
    val apellidos = remember { mutableStateOf("") }
    val correo = remember { mutableStateOf("") }
    val contrasena = remember { mutableStateOf("") }
    val repetirContrasena = remember { mutableStateOf("") }
    val dni = remember { mutableStateOf("") }
    val telefono = remember { mutableStateOf("") }
    val direccion = remember { mutableStateOf("") }
    val errorMessage = remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            if (currentScreen != TecnisisScreen.Login) {
                TecnisisTopAppBar(onProfileClick = {})
            }
        },
        floatingActionButton = {
            TecnisisFloatingActionButton(
                currentScreen = currentScreen,
                navController = navController,
                snackbarHostState = snackbarHostState,
                coroutineScope = coroutineScope,
                userRepository = userRepository,
                nombre = nombre,
                apellidos = apellidos,
                correo = correo,
                contrasena = contrasena,
                repetirContrasena = repetirContrasena,
                dni = dni,
                telefono = telefono,
                direccion = direccion,
                onFloatingButtonClick = floatingButtonPressed,
                errorMessage = errorMessage
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = TecnisisScreen.Login.name,
            modifier = Modifier
                .padding(innerPadding)
                .background(color = Color.Transparent)
        ) {
            composable(route = TecnisisScreen.Login.name) {
                LoginScreen(
                    onSignUp = { navController.navigate(TecnisisScreen.SignUp.name) },
                    onEmailChange = { correo.value = it },
                    onPasswordChange = { contrasena.value = it }
                )
            }
            composable(route = TecnisisScreen.SignUp.name) {
                SignUpScreen(
                    viewModel = viewModel(modelClass = SignUpViewModel::class.java),
                    navController = navController,
                    onSignUp = floatingButtonPressed/*,
                    userRepository = userRepository,
                    nombre = nombre,
                    apellidos = apellidos,
                    correo = correo,
                    contrasena = contrasena,
                    repetirContrasena = repetirContrasena,
                    dni = dni,
                    telefono = telefono,
                    direccion = direccion,
                    errorMessage = errorMessage*/
                )
            }
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

@Composable
fun TecnisisFloatingActionButton(
    currentScreen: TecnisisScreen,
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    userRepository: UserRepository,
    nombre: MutableState<String>,
    apellidos: MutableState<String>,
    correo: MutableState<String>,
    contrasena: MutableState<String>,
    repetirContrasena: MutableState<String>,
    dni: MutableState<String>,
    telefono: MutableState<String>,
    direccion: MutableState<String>,
    onFloatingButtonClick: MutableState<() -> Unit>,
    errorMessage: MutableState<String>
) {
    val icon = when (currentScreen) {
        TecnisisScreen.Login -> Icons.AutoMirrored.Filled.ArrowForward
        TecnisisScreen.SignUp -> Icons.Default.ArrowForward
        TecnisisScreen.ListRequests -> Icons.Default.Add
        TecnisisScreen.StartRequest -> Icons.Default.Save
    }

    FloatingActionButton(
        onClick = onFloatingButtonClick.value/*
        {
            when (currentScreen) {
                TecnisisScreen.Login -> {
                    coroutineScope.launch {
                        if (correo.value.isNotBlank() && contrasena.value.isNotBlank()) {
                            val userExists = userRepository.validateUser(correo.value, contrasena.value)
                            if (userExists) {
                                navController.navigate(TecnisisScreen.ListRequests.name)
                            } else {
                                snackbarHostState.showSnackbar("Usuario o contraseña incorrectos")
                            }
                        } else {
                            snackbarHostState.showSnackbar("Por favor, complete todos los campos")
                        }
                    }
                }
                TecnisisScreen.SignUp -> {
                    // Validar que todos los campos estén completos
                    if (nombre.value.isBlank() || apellidos.value.isBlank() || correo.value.isBlank() ||
                        contrasena.value.isBlank() || repetirContrasena.value.isBlank() || dni.value.isBlank() ||
                        telefono.value.isBlank() || direccion.value.isBlank()
                    ) {
                        errorMessage.value = "Por favor, complete todos los campos"
                    } else if (contrasena.value != repetirContrasena.value) {
                        errorMessage.value = "Las contraseñas no coinciden"
                    } else {
                        // Campos completos y contraseñas coinciden
                        val usuario = Usuario(0, correo.value, contrasena.value)
                        val persona = Persona(
                            idPersona = 0,
                            nombre = nombre.value,
                            apellido = apellidos.value,
                            telefono = telefono.value,
                            direccion = direccion.value,
                            dni = dni.value,
                            sexo = "Otro",
                            tipo = 1,
                            idUsuario = 0
                        )
                        coroutineScope.launch {
                            try {
                                userRepository.registerUserWithPersona(usuario, persona)
                                errorMessage.value = ""
                                snackbarHostState.showSnackbar("Registro correcto")
                                navController.navigate(TecnisisScreen.Login.name)
                            } catch (e: Exception) {
                                snackbarHostState.showSnackbar("Error al registrar usuario: ${e.message}")
                            }
                        }
                    }
                }
                TecnisisScreen.ListRequests -> {
                    navController.navigate(TecnisisScreen.StartRequest.name)
                }
                TecnisisScreen.StartRequest -> {
                    navController.navigate(TecnisisScreen.ListRequests.name)
                }
            }
        }*/,
        containerColor = Color.Red,
        modifier = Modifier.size(60.dp)
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = Color.White)
    }

    // Mostrar mensaje de error, si existe
    if (errorMessage.value.isNotEmpty()) {
        LaunchedEffect(errorMessage.value) {
            snackbarHostState.showSnackbar(errorMessage.value)
            errorMessage.value = "" // Limpiar el mensaje después de mostrarlo
        }
    }
}
/*
@Composable
fun provideUsuarioRepository(context: Context): UserRepository {
    val database = AppDatabase.getDatabase(context)
    return UserRepository(database.usuarioDao(), database.personaDao())
}*/

@Preview(showBackground = true)
@Composable
fun TecnisisAppPreview() {
    /*val fakeUsuarioDao = object : UsuarioDao {
        override suspend fun validateUser(email: String, password: String): Usuario? {
            return if (email == "preview@example.com" && password == "preview") Usuario(1, email, password) else null
        }

        override suspend fun insert(usuario: Usuario): Long {
            return 1L
        }
    }

    val fakePersonaDao = object : PersonaDao {
        override suspend fun insert(persona: Persona) { }
        override suspend fun update(persona: Persona) { }
        override suspend fun getAllPersonas(): List<Persona> = emptyList()
        override suspend fun getPersonaById(idPersona: Int): Persona? = null
        override suspend fun getPersonasByUsuarioId(idUsuario: Int): List<Persona> = emptyList()
        override suspend fun deletePersonaById(idPersona: Int) { }
    }

    val fakeRepository = UserRepository(fakeUsuarioDao, fakePersonaDao)

    TecnisisTheme {
        TecnisisApp(userRepository = fakeRepository)
    }*/
}
