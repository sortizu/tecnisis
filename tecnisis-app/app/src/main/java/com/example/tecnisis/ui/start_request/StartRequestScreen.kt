package com.example.tecnisis.ui.start_request

import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tecnisis.R
import com.example.tecnisis.TecnisisScreen
import com.example.tecnisis.ui.components.BottomPattern
import com.example.tecnisis.ui.components.CustomBasicTextField
import com.example.tecnisis.ui.components.CustomDatePickerField
import com.example.tecnisis.ui.components.CustomFloatingButton
import com.example.tecnisis.ui.components.CustomNumberField
import com.example.tecnisis.ui.components.ScreenTitle
import com.example.tecnisis.ui.components.SelectableListItem
import com.example.tecnisis.ui.components.SingleChoiceDialog
import com.example.tecnisis.ui.components.TecnisisTopAppBar
import com.example.tecnisis.ui.components.TopBarState
import com.example.tecnisis.ui.components.uriToBase64
import kotlinx.coroutines.delay

@Composable
fun StartRequestScreen(
    currentScreen: TecnisisScreen = TecnisisScreen.StartRequest,
    viewModel: StartRequestViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    floatingButton: MutableState<@Composable () -> Unit>,
    topAppBar: MutableState<@Composable () -> Unit>,
    editable: Boolean = true
){
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val techniques = uiState.techniques//by viewModel.techniques.observeAsState(emptyList())
    val isDialogVisible = uiState.isDialogVisible //by viewModel.isDialogVisible.observeAsState(false)
    val message = uiState.message

    topAppBar.value = {
        TecnisisTopAppBar(
            state = TopBarState.COLLAPSED
        )
    }

    floatingButton.value = {
        CustomFloatingButton(
            onClick = { viewModel.startRequest() },
            icon = Icons.Default.Save
        )
    }

    LaunchedEffect(uiState.creationSuccessful) {
        if (uiState.creationSuccessful) {
            // Waits half a second before navigating to the ListRequests screen
            delay(100)
            navController.navigate(TecnisisScreen.ListRequests.name)
        }
    }

    LaunchedEffect(message) {
        if (message.isNotEmpty()){
            snackbarHostState.showSnackbar(message)
            viewModel.resetMessage()
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val imageBase64 = uriToBase64(context, it)
            viewModel.updateImage(imageBase64)
        }
    }

        Column(
            modifier = Modifier
                .padding(0.dp)
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {


            ScreenTitle(text = context.getString(currentScreen.title))
            // Image upload area
            ImageUploadSection(
                imageBase64 = uiState.image,
                onAddImageButtonClick = { launcher.launch("image/jpeg")}
            )

            // Input fields

            CustomBasicTextField(
                value = uiState.artworkTitle,
                onValueChange = { viewModel.updateArtworkTitle(it) },
                label = stringResource(R.string.artwork_title),
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CustomNumberField(
                    value = uiState.width.toString(),
                    onValueChange = { viewModel.updateWidth(it.toDoubleOrNull() ?: 0.0) },
                    label = stringResource(R.string.width),
                    modifier = Modifier.weight(1f)
                )
                CustomNumberField(
                    value = uiState.height.toString(),
                    onValueChange = { viewModel.updateHeight(it.toDoubleOrNull() ?: 0.0) },
                    label = stringResource(R.string.height),
                    modifier = Modifier.weight(1f)
                )
            }
            CustomDatePickerField(
                defaultDate = uiState.date,
                onDateSelected = { viewModel.updateDate(it) }
            )
            SelectableListItem(
                text = stringResource(R.string.select_technique),
                icon = Icons.Default.ContentCut,
                iconDescription = "",
                onClick = {
                    viewModel.updateIsDialogVisible(true)
                }
            )
            BottomPattern()
            if (isDialogVisible) {
                SingleChoiceDialog(
                    title = "Seleccionar tecnica",
                    radioOptions = techniques.map { it.name },
                    indexOfDefault = uiState.techniqueIndex,
                    isDialogVisible = {visibility -> viewModel.updateIsDialogVisible(visibility)},
                    onSelectedItemIndex = { index -> viewModel.updateTechniqueId(index) },
                )
            }
        }
}

@Composable
fun ImageUploadSection(
    imageBase64: String,
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
            if (imageBase64.isNotEmpty()) {
                val imageBytes = Base64.decode(imageBase64, Base64.DEFAULT)
                val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "Selected Image",
                    modifier = Modifier.size(100.dp)
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.media),
                    contentDescription = "Upload Placeholder",
                    modifier = Modifier.size(100.dp)
                )
            }
        }
        Button(
            onClick = { onAddImageButtonClick() },
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
}