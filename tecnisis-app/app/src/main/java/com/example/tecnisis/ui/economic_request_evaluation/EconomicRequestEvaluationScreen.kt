package com.example.tecnisis.ui.economic_request_evaluation

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tecnisis.R
import com.example.tecnisis.TecnisisScreen
import com.example.tecnisis.ui.components.BottomPattern
import com.example.tecnisis.ui.components.CustomDatePickerField
import com.example.tecnisis.ui.components.CustomFloatingButton
import com.example.tecnisis.ui.components.CustomNumberField
import com.example.tecnisis.ui.components.HighlightButton
import com.example.tecnisis.ui.components.ImageCard
import com.example.tecnisis.ui.components.ScreenTitle
import com.example.tecnisis.ui.components.SelectableListItem
import com.example.tecnisis.ui.components.TecnisisTopAppBar
import com.example.tecnisis.ui.components.TopBarState
import com.example.tecnisis.ui.components.uriToBase64
import kotlinx.coroutines.delay

@Composable
fun EconomicRequestEvaluationScreen(
    currentScreen: TecnisisScreen = TecnisisScreen.EconomicRequestEvaluation,
    viewModel: EconomicRequestEvaluationViewModel,
    modifier: Modifier = Modifier,
    navController: NavController,
    floatingButton: MutableState<@Composable () -> Unit>,
    topAppBar: MutableState<@Composable () -> Unit>,
    snackbarHostState: SnackbarHostState,
    editable: Boolean = false
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val request = uiState.request
    val message by viewModel.message.observeAsState("")


    topAppBar.value = {
        TecnisisTopAppBar(
            state = TopBarState.COLLAPSED
        )
    }

    floatingButton.value = {
        if (editable) {
            CustomFloatingButton(
                onClick = { viewModel.saveReview() },
                icon = Icons.Default.Save
            )
        }
    }

    LaunchedEffect(uiState.evaluationSaved) {
        if (uiState.evaluationSaved) {
            delay(100)
            navController.navigate(TecnisisScreen.ListRequests.name)
        }
    }

    LaunchedEffect(message) {
        if (message.isNotEmpty()) {
            delay(100)
            snackbarHostState.showSnackbar(message)
            viewModel.updateMessage("")
        }
    }
    val pdfLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val pdfBase64 = uriToBase64(context, it)
            viewModel.updateReviewDocument(pdfBase64)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .padding(0.dp)
                .padding(start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            ScreenTitle(text = context.getString(currentScreen.title))
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }

                request != null -> {
                    ImageCard(
                        image = request.artWork.image,
                        title = request.artWork.title,
                        date = request.artWork.creationDate,
                        dimensions = request.artWork.width.toString() + " x " + request.artWork.height.toString()
                    )
                    SelectableListItem(
                        text = "Resultados de evaluacion artística",
                        icon = Icons.Default.Check,
                        iconDescription = "resultados",
                        onClick = {
                            navController.navigate(TecnisisScreen.ArtisticRequestEvaluation.name + "/${request.id}/false")
                        }

                    )
                    // Input fields

                    CustomNumberField(
                        label = "Precio de venta",
                        value = uiState.salePrice.toString(),
                        onValueChange = {
                            viewModel.updateSalePrice(it.toDouble())
                        },
                        modifier = Modifier.fillMaxWidth(),
                        editable = editable
                    )
                    CustomNumberField(
                        label = "Porcentaje de ganancia",
                        value = uiState.galleryPercentage.toString(),
                        onValueChange = {
                            viewModel.updateGalleryPercentage(it.toDouble())
                        },
                        modifier = Modifier.fillMaxWidth(),
                        editable = editable
                    )
                    CustomDatePickerField(
                        defaultDate = uiState.date,
                        onDateSelected = {
                            viewModel.updateDate(it)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        editable = false
                    )
                    if (editable) {
                        HighlightButton(
                            text = stringResource(R.string.attach_review_document),
                            onClick = {pdfLauncher.launch("application/pdf")}
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

            }

        }
        Spacer(modifier = Modifier.weight(1f))
        BottomPattern()
    }
}