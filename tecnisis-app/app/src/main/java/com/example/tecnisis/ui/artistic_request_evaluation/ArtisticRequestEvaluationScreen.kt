package com.example.tecnisis.ui.artistic_request_evaluation

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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
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
import androidx.navigation.NavController
import com.example.tecnisis.R
import com.example.tecnisis.TecnisisScreen
import com.example.tecnisis.ui.components.CustomDatePickerField
import com.example.tecnisis.ui.components.CustomNumberField
import com.example.tecnisis.ui.components.CustomSingleChoiceSegmentedButton

import com.example.tecnisis.ui.components.HighlightButton
import com.example.tecnisis.ui.components.ImageCard
import com.example.tecnisis.ui.components.ScreenTitle
import com.example.tecnisis.ui.components.SelectableListItem
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtisticRequestReviewScreen(
    currentScreen: TecnisisScreen = TecnisisScreen.ArtisticRequestEvaluation,
    viewModel: ArtisticRequestEvaluationViewModel,
    modifier: Modifier = Modifier,
    navController: NavController,
    floatingButtonPressed: MutableState<() -> Unit>,
){
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val request = uiState.request
    val message by viewModel.message.observeAsState("")
    val options = listOf("Aprobar", "Desaprobar")

    floatingButtonPressed.value = {
        viewModel.saveReview()
    }

    LaunchedEffect(uiState.evaluationSaved) {
        if (uiState.evaluationSaved) {
            delay(500)
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
        val context = LocalContext.current
        ScreenTitle(text = context.getString(currentScreen.title))
        // Image upload area
        ImageCard(
            image = request!!.artWork.image,
            title = request.artWork.title,
            date = request.artWork.creationDate,
            dimensions = request?.artWork?.width.toString() + " x " + request?.artWork?.height.toString()
        )
        SelectableListItem(
            text = request.artWork.artist.person.name,
            icon = Icons.Default.Person,
            iconDescription = "Artist"
        )
        CustomSingleChoiceSegmentedButton(
            options = options,
            onSelectionChanged = {
                viewModel.updateResult(options[it])
            }
        )
        // Input fields

        CustomNumberField(
            label = "Calificación (N/100)",
            value = "0.0",
            onValueChange = {
                viewModel.updateRating(it.toFloat())
            },
            modifier = Modifier.fillMaxWidth()
        )
        CustomDatePickerField(
            defaultDate = "",
            onDateSelected = {
                viewModel.updateDate(it)
            }
        )
        HighlightButton(
            text = stringResource(R.string.attach_review_document),
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ArtisticRequestReviewScreenPreview() {

}