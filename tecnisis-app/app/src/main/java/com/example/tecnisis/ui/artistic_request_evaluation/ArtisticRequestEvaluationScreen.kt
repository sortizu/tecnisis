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
import com.example.tecnisis.ui.components.CustomNumberField
import com.example.tecnisis.ui.components.CustomSingleChoiceSegmentedButton
import com.example.tecnisis.ui.components.DatePickerDocked
import com.example.tecnisis.ui.components.HighlightButton
import com.example.tecnisis.ui.components.ImageCard
import com.example.tecnisis.ui.components.ScreenTitle
import com.example.tecnisis.ui.components.SelectableListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtisticRequestReviewScreen(
    currentScreen: TecnisisScreen = TecnisisScreen.ArtisticRequestEvaluation,
    viewModel: ArtisticRequestEvaluationViewModel = ArtisticRequestEvaluationViewModel(),
    modifier: Modifier = Modifier
){
    val context = LocalContext.current
    val rating by viewModel.rating.observeAsState(0.0f)
    val result by viewModel.result.observeAsState("")
    val reviewDocument by viewModel.reviewDocument.observeAsState("")
    val date by viewModel.date.observeAsState("")
    val message by viewModel.message.observeAsState("")
    val options = listOf("Aprobar", "Desaprobar")
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
            imageResource = R.drawable.media,
            title = "Artwork Title",
            date = "dd/MM/YYYY",
            dimensions = "ww x hh"
        )
        SelectableListItem(
            text = "Artist: Artist 1",
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
        DatePickerDocked()
        HighlightButton(
            text = stringResource(R.string.attach_review_document),
            onClick = {}
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
            Text(text = "Inspeccionar foto")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArtisticRequestReviewScreenPreview() {
    ArtisticRequestReviewScreen()
}