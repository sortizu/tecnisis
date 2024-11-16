package com.example.tecnisis.ui.view_request

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tecnisis.R
import com.example.tecnisis.TecnisisScreen
import com.example.tecnisis.ui.components.HighlightButton
import com.example.tecnisis.ui.components.ImageCard
import com.example.tecnisis.ui.components.ProgressCard
import com.example.tecnisis.ui.components.ScreenTitle
import com.example.tecnisis.ui.components.SectionHeader
import com.example.tecnisis.ui.components.SelectableListItem
import com.example.tecnisis.ui.theme.TecnisisTheme

@Composable
fun ViewRequestScreen(
    currentScreen: TecnisisScreen = TecnisisScreen.ArtisticRequestReview,
    viewModel: ViewRequestViewModel = ViewRequestViewModel(),
    requestId: Int,
    modifier: Modifier = Modifier
){
    val request = viewModel.request.observeAsState()
    val message = viewModel.message.observeAsState()
    val isLoading = viewModel.isLoading.observeAsState()
    viewModel.getRequest(requestId)
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(0.dp)
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ScreenTitle(text = context.getString(currentScreen.title))
        when {
            isLoading.value == true -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            else -> {
                ImageCard(
                    imageResource = R.drawable.media,
                    title = "Artwork Title",
                    date = "dd/MM/YYYY",
                    dimensions = "ww x hh"
                )
                HighlightButton(
                    onClick = { },
                    text = stringResource(R.string.inspect_image)
                )
                SelectableListItem(
                    text = stringResource(R.string.technique),
                    icon = Icons.Filled.ContentCut,
                    iconDescription = stringResource(R.string.technique)
                )
                SectionHeader(text = stringResource(R.string.request_progress),
                    Modifier
                        .align(Alignment.Start)
                        .padding(vertical = 8.dp)
                )
                request.value?.let {
                    ProgressCard(
                        order = 1,
                        status = it.request.status,
                        stepName = stringResource(R.string.specialist_selection)
                    )
                    if (it.artisticEvaluation != null){
                        ProgressCard(
                            order = 2,
                            status = it.artisticEvaluation.result,
                            stepName = stringResource(R.string.artistic_evaluation)
                        )
                    }else if(it.request.status == "Aprobada"){
                        ProgressCard(
                            order = 2,
                            status = "Pendiente",
                            stepName = stringResource(R.string.artistic_evaluation)
                        )
                    }
                    if(it.economicEvaluation != null){
                        ProgressCard(
                            order = 3,
                            status = stringResource(R.string.finished),
                            stepName = stringResource(R.string.economic_evaluation)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ViewRequestScreenPreview() {
    TecnisisTheme {
        ViewRequestScreen(requestId = 1)
    }
}