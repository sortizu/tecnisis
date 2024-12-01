package com.example.tecnisis.ui.economic_request_evaluation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
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
import kotlinx.coroutines.delay

@Composable
fun EconomicRequestEvaluationScreen(
    currentScreen: TecnisisScreen = TecnisisScreen.EconomicRequestEvaluation,
    viewModel: EconomicRequestEvaluationViewModel,
    modifier: Modifier = Modifier,
    navController: NavController,
    floatingButton: MutableState<@Composable () -> Unit>,
    topAppBar: MutableState<@Composable () -> Unit>
){
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
        CustomFloatingButton(
            onClick = { viewModel.saveReview() },
            icon = Icons.Default.Add
        )
    }

    LaunchedEffect(uiState.evaluationSaved) {
        if (uiState.evaluationSaved) {
            delay(100)
            navController.navigate(TecnisisScreen.ListRequests.name)
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
        ImageCard(
            image = request!!.artWork.image,
            title = request.artWork.title,
            date = request.artWork.creationDate,
            dimensions = request?.artWork?.width.toString() + " x " + request?.artWork?.height.toString()
        )
        SelectableListItem(
            text = "Resultados de evaluacion artística",
            icon = Icons.Default.Check,
            iconDescription = "resultados"
        )
        // Input fields

        CustomNumberField(
            label = "Precio de venta",
            value = "0.0",
            onValueChange = {
                viewModel.updateSalePrice(it.toDouble())
            },
            modifier = Modifier.fillMaxWidth()
        )
        CustomNumberField(
            label = "Porcentaje de ganancia",
            value = "0",
            onValueChange = {
                viewModel.updateGalleryPercentage(it.toDouble())
            },
            modifier = Modifier.fillMaxWidth()
        )
        CustomDatePickerField(
            defaultDate = "",
            onDateSelected = {
                viewModel.updateDate(it.toString())
            }
        )
        HighlightButton(
            text = stringResource(R.string.attach_review_document),
            onClick = {}
        )
        BottomPattern()
    }
}

@Preview(showBackground = true)
@Composable
fun ArtisticRequestReviewScreenPreview() {
}