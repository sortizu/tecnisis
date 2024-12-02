package com.example.tecnisis.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.tecnisis.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomBasicTextField(label: String, value: String, modifier: Modifier = Modifier ,onValueChange: (String) -> Unit, editable : Boolean = true){
    val inputBackgroundColor = Color(0xFFFFEBEE)
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = inputBackgroundColor,
            unfocusedContainerColor = inputBackgroundColor
        ),
        enabled = editable
    )
}

@Composable
fun CustomEmailField(label: String, value: String, modifier: Modifier = Modifier, onValueChange: (String) -> Unit, editable : Boolean = true){
    val inputBackgroundColor = Color(0xFFFFEBEE)
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = inputBackgroundColor,
            unfocusedContainerColor = inputBackgroundColor
        ),
        enabled = editable
    )
}

@Composable
fun CustomPasswordField(label: String, value: String, modifier: Modifier = Modifier, onValueChange: (String) -> Unit, editable : Boolean = true) {
    val inputBackgroundColor = Color(0xFFFFEBEE)
    var passwordVisible by remember { mutableStateOf(false) }

    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = inputBackgroundColor,
            unfocusedContainerColor = inputBackgroundColor
        ),
        trailingIcon = {
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(
                    imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                    contentDescription = if (passwordVisible) "Hide password" else "Show password"
                )
            }
        },
        enabled = editable
    )
}

@Composable
fun CustomNumberField(label: String, value: String, modifier: Modifier = Modifier, onValueChange: (String) -> Unit, editable : Boolean = true){
    val inputBackgroundColor = Color(0xFFFFEBEE)
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = inputBackgroundColor,
            unfocusedContainerColor = inputBackgroundColor
        ),
        enabled = editable
    )
}

@Composable
fun CustomPhoneNumberField(label: String, value: String, modifier: Modifier = Modifier, onValueChange: (String) -> Unit, editable : Boolean = true){
    val inputBackgroundColor = Color(0xFFFFEBEE)
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = inputBackgroundColor,
            unfocusedContainerColor = inputBackgroundColor
        ),
        enabled = editable
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePickerField(
    label: String = stringResource(R.string.date_of_realisation),
    defaultDate: String? = null,
    onDateSelected: (Long) -> Unit,
    modifier: Modifier = Modifier,
    editable : Boolean = true
) {
    val initalSelectedDateMillis = defaultDate?.let {
        SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).parse(it)?.time
    }
    var showDatePickerDialog by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf(defaultDate) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initalSelectedDateMillis
    )

    OutlinedTextField(
        value = selectedDate ?: "",
        onValueChange = { },
        label = { Text(label) },
        readOnly = true,
        trailingIcon = {
            IconButton(onClick = {
            if (editable){
                showDatePickerDialog = true
            }
            }) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Select date"
                )
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp),
        enabled = editable
    )

    if (showDatePickerDialog) {
        AlertDialog(
            onDismissRequest = { showDatePickerDialog = false },
            title = { Text(stringResource(R.string.select_a_date)) },
            text = {
                DatePicker(
                    state = datePickerState,
                    showModeToggle = false
                )
            },
            confirmButton = {
                TextButton(
                    text = "OK",
                    onClick = {
                        datePickerState.selectedDateMillis?.let {
                            onDateSelected(it)
                            selectedDate = convertMillisToDate(it)
                        }
                        showDatePickerDialog = false
                    }
                )
            },
            dismissButton = {
                TextButton(
                    text = "Cancel",
                    onClick = { showDatePickerDialog = false }
                )
            }
        )
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}