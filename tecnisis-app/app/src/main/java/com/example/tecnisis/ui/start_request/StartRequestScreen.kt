package com.example.tecnisis.ui.start_request

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tecnisis.R
import com.example.tecnisis.TecnisisScreen
import com.example.tecnisis.ui.components.DatePickerDocked
import com.example.tecnisis.ui.components.ScreenTitle
import com.example.tecnisis.ui.components.SelectableListItem

@Composable
fun StartRequestScreen(
    currentScreen: TecnisisScreen = TecnisisScreen.StartRequest,
    modifier: Modifier = Modifier
){
    Column(
        modifier = Modifier
            .padding(0.dp)
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val context = LocalContext.current
        ScreenTitle(text = context.getString(currentScreen.title))
        // Image upload area
        ImageUploadSection()

        // Input fields
        InputField(value = "", onValueChange = { }, label = "Título de obra")
        InputField(value = "", onValueChange = {}, label = "Dimensiones")
        DatePickerDocked()
        SelectableListItem(
            text = stringResource(R.string.select_technique),
            icon = Icons.Default.ContentCut,
            iconDescription = ""
        )
    }
}

@Composable
fun ImageUploadSection(
    onAddImageButtonClick: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(150.dp)
                .border(2.dp, Color.Gray, RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.media),
                contentDescription = "Upload Placeholder",
                modifier = Modifier.size(100.dp)
            )
        }
        Button(
            onClick = { onAddImageButtonClick },
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFA643),
                contentColor = Color.Black
            ),
        ) {
            Text(text = "Agregar foto")
        }
    }
}

@Composable
fun InputField(value: String, onValueChange: (String) -> Unit, label: String) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()

    )
}

@Preview(showBackground = true)
@Composable
fun StartRequestScreenPreview() {
    StartRequestScreen()
}