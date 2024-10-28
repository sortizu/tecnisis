package com.example.tecnisis.ui.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.TextFieldValue
import com.example.tecnisis.R
import androidx.compose.material3.*
import androidx.compose.ui.layout.ContentScale
import com.example.tecnisis.data.Solicitud
import com.example.tecnisis.data.SolicitudWithObra
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Popup
import androidx.compose.ui.zIndex
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun FloatingButton(
    onClick: () -> Unit,
    icon: ImageVector,
    modifier: Modifier = Modifier
){
    FloatingActionButton(
        onClick = onClick,
        containerColor = Color.White,
        contentColor = Color.Red,
        modifier = modifier
            .size(75.dp)
//            .offset(x = screenWidth - 100.dp, y = screenHeight - 150.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "Add Request",
            modifier = Modifier.size(32.dp)
        )
    }
}

// Patron de la parte inferior de la pantalla
@Composable
fun BottomPattern() {
    val navigationBarPadding = WindowInsets.navigationBars.asPaddingValues()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(navigationBarPadding),
        contentAlignment = Alignment.BottomCenter
    ) {
        Image(
            painter = painterResource(id = R.drawable.pattern),
            contentDescription = "Bottom Pattern",
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            contentScale = ContentScale.FillWidth
        )
    }
}

// "HorizontalDivider" que muestra el titulo de la interfaz
@Composable
fun ScreenTitle(modifier: Modifier = Modifier, text: String) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        HorizontalDivider(thickness = 1.dp)
        Text(text = text, style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color.Gray))
    }
}

@Composable
fun RequestList(solicitudes: List<SolicitudWithObra>) {
    LazyColumn {
        items(solicitudes.size) { index ->
            RequestCard(index+1,solicitudes[index])
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun RequestCard(orden: Int, solicitudWithObra: SolicitudWithObra) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            // Aplica padding horizontal solo a la izquierda
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 0.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Priority Circle
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(Color.Red),
                contentAlignment = Alignment.Center
            ) {
                Text(text = orden.toString(), color = Color.White)
            }

            // Detalles de la solicitud
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                Text(text = solicitudWithObra.obra.titulo , fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(text = solicitudWithObra.solicitud.estado , fontSize = 14.sp, color = Color.Gray)
            }
            // Imagen de la obra
            Image(
                painter = painterResource(id = R.drawable.media),
                contentDescription = "Obra",
                modifier = Modifier.size(100.dp)
            )
        }
    }
}

@Composable
fun InfoBox(
    modifier: Modifier = Modifier,
    description: String
) {
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
            text = description,
            color = Color.Black,
            fontSize = 14.sp
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDocked() {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = selectedDate,
            onValueChange = { },
            label = { Text(stringResource(R.string.date_of_realisation)) },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showDatePicker = !showDatePicker }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select date"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        )

        if (showDatePicker) {
            Popup(
                onDismissRequest = { showDatePicker = false },
                alignment = Alignment.TopStart
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = 64.dp)
                        .shadow(elevation = 4.dp)
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(16.dp)
                ) {
                    DatePicker(
                        state = datePickerState,
                        showModeToggle = false
                    )
                }
            }
        }
    }
}

@Composable
fun DatePickerFieldToModal(modifier: Modifier = Modifier) {
    var selectedDate by remember { mutableStateOf<Long?>(null) }
    var showModal by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = selectedDate?.let { convertMillisToDate(it) } ?: "",
        onValueChange = { },
        label = { Text(stringResource(R.string.date_of_realisation)) },
        placeholder = { Text("MM/DD/YYYY") },
        trailingIcon = {
            Icon(Icons.Default.DateRange, contentDescription = "Select date")
        },
        modifier = modifier
            .fillMaxWidth()
            .pointerInput(selectedDate) {
                awaitEachGesture {
                    // Modifier.clickable doesn't work for text fields, so we use Modifier.pointerInput
                    // in the Initial pass to observe events before the text field consumes them
                    // in the Main pass.
                    awaitFirstDown(pass = PointerEventPass.Initial)
                    val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                    if (upEvent != null) {
                        showModal = true
                    }
                }
            }
    )
/*
    if (showModal) {
        DatePickerModal(
            onDateSelected = { selectedDate = it },
            onDismiss = { showModal = false }
        )
    }*/
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

@Composable
fun SelectableListItem(
    text: String,
    icon: ImageVector,
    iconDescription: String
) {
    Column {
        ListItem(
            headlineContent = { Text(text) },
            leadingContent = {
                Icon(
                    icon,
                    contentDescription = iconDescription,
                )
            },
            trailingContent = {
                Icon(
                    Icons.Default.ArrowRight,
                    contentDescription = "Open Modal"
                )
            }
        )
        HorizontalDivider()
    }
}