package com.example.tecnisis.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomBasicTextField(label: String, value: String, modifier: Modifier = Modifier ,onValueChange: (String) -> Unit){
    val inputBackgroundColor = Color(0xFFFFEBEE)
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = inputBackgroundColor,
            unfocusedContainerColor = inputBackgroundColor
        )
    )
}

@Composable
fun CustomEmailField(label: String, value: String, modifier: Modifier = Modifier, onValueChange: (String) -> Unit){
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
        )
    )
}

@Composable
fun CustomPasswordField(label: String, value: String, modifier: Modifier = Modifier, onValueChange: (String) -> Unit) {
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
        }
    )
}

@Composable
fun CustomNumberField(label: String, value: String, modifier: Modifier = Modifier, onValueChange: (String) -> Unit){
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
        )
    )
}

@Composable
fun CustomPhoneNumberField(label: String, value: String, modifier: Modifier = Modifier, onValueChange: (String) -> Unit){
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
        )
    )
}