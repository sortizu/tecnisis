package com.example.tecnisis.ui.start_request

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import com.example.tecnisis.ui.components.CustomBasicTextField
import com.example.tecnisis.ui.components.CustomNumberField
import com.example.tecnisis.ui.components.DatePickerDocked
import com.example.tecnisis.ui.components.ScreenTitle
import com.example.tecnisis.ui.components.SelectableListItem
import com.example.tecnisis.ui.components.SingleChoiceDialog

@Composable
fun StartRequestScreen(
    currentScreen: TecnisisScreen = TecnisisScreen.StartRequest,
    viewModel: StartRequestViewModel = StartRequestViewModel(),
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
        val artworkTitle by viewModel.artworkTitle.observeAsState("")
        val width by viewModel.width.observeAsState(0.0)
        val height by viewModel.height.observeAsState(0.0)
        val date by viewModel.date.observeAsState("")
        val technique by viewModel.techniqueId.observeAsState(0)
        val message by viewModel.message.observeAsState("")
        val image by viewModel.image.observeAsState("")
        val techniques by viewModel.techniqueResponses.observeAsState(listOf())
        val isDialogVisible by viewModel.isDialogVisible.observeAsState(false)

        viewModel.loadTechniques()

        ScreenTitle(text = context.getString(currentScreen.title))
        // Image upload area
        ImageUploadSection()

        // Input fields

        CustomBasicTextField(
            value = artworkTitle,
            onValueChange = {viewModel.updateArtworkTitle(it) },
            label = stringResource(R.string.artwork_title),
            modifier = Modifier.fillMaxWidth()
        )
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            CustomNumberField(value = width.toString(),onValueChange = { viewModel.updateWidth(it.toDoubleOrNull() ?: 0.0)}, label = stringResource(R.string.width), modifier = Modifier.weight(1f))
            CustomNumberField(value = height.toString(),onValueChange = { viewModel.updateHeight(it.toDoubleOrNull() ?: 0.0)}, label = stringResource(R.string.height), modifier = Modifier.weight(1f))
        }
        DatePickerDocked(
            onValueChange = { viewModel.updateDate(it) }
        )
        SelectableListItem(
            text = stringResource(R.string.select_technique),
            icon = Icons.Default.ContentCut,
            iconDescription = "",
            onClick = {
                viewModel.updateIsDialogVisible(true)
            }
        )
        if (isDialogVisible) {
            SingleChoiceDialog(
                title = "Seleccionar tecnica",
                radioOptions = techniques.map { it.name },
                indexOfDefault = 0,
                isDialogVisible = { },
                onSelectedItemIndex = { index -> viewModel.updateTechniqueId(techniques[index].idTechnique)},
            )
        }
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

@Preview(showBackground = true)
@Composable
fun StartRequestScreenPreview() {
    StartRequestScreen()
}