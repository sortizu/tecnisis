package com.example.tecnisis.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun HighlightButton(onClick: () -> Unit, text: String){
    Button(
        onClick = onClick,
        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFFA643),
            contentColor = Color.Black
        ),
    ) {
        Text(text = text)
    }
}

@Composable
fun BasicButton(text: String, onClick: () -> Unit){
    Button(
        onClick = onClick,
        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFFE2DD),
            contentColor = Color(0xFFA50007)
        ),
    ) {
        Text(text = text)
    }
}

@Composable
fun TextButton(text: String, onClick: () -> Unit){
    Text(
        text = text,
        color = Color(0xFFA50007),
        modifier = Modifier.clickable {
            onClick()
        }
    )
}

@Composable
fun CustomFloatingButton(
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
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "Floating Button Icon",
            modifier = Modifier.size(32.dp)
        )
    }
}