package com.example.tecnisis.ui.view_request

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCut
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tecnisis.R
import com.example.tecnisis.TecnisisScreen
import com.example.tecnisis.ui.components.BottomPattern
import com.example.tecnisis.ui.components.HighlightButton
import com.example.tecnisis.ui.components.ImageCard
import com.example.tecnisis.ui.components.ProgressCard
import com.example.tecnisis.ui.components.ScreenTitle
import com.example.tecnisis.ui.components.SectionHeader
import com.example.tecnisis.ui.components.SelectableListItem
import com.example.tecnisis.ui.components.TecnisisTopAppBar
import com.example.tecnisis.ui.components.TopBarState
import com.example.tecnisis.ui.theme.TecnisisTheme

@Composable
fun ViewRequestScreen(
    currentScreen: TecnisisScreen = TecnisisScreen.ViewRequest,
    viewModel: ViewRequestViewModel,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    floatingButton: MutableState<@Composable () -> Unit>,
    topAppBar: MutableState<@Composable () -> Unit>,
    navController: NavHostController
){
    val uiState by viewModel.uiState.collectAsState()
    val message = viewModel.message.observeAsState()
    val request = uiState.request
    val artisticEvaluation = uiState.artisticEvaluation
    val economicEvaluation = uiState.economicEvaluation

    val context = LocalContext.current

    topAppBar.value = {
        TecnisisTopAppBar(
            state = TopBarState.COLLAPSED
        )
    }

    floatingButton.value = {}

    LaunchedEffect(message.value){
        message.value?.let {
            snackbarHostState.showSnackbar(it)
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

                else -> {
                    ImageCard(
                        image = request?.artWork?.image ?: "",
                        title = request?.artWork?.title ?: "",
                        date = request?.date ?: "",
                        dimensions = request?.artWork?.width.toString() + " x " + request?.artWork?.height.toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(10.dp))
                    )
                    HighlightButton(
                        onClick = { },
                        text = stringResource(R.string.inspect_image)
                    )
                    SelectableListItem(
                        text = stringResource(R.string.technique) + ": " + request?.artWork?.technique?.name,
                        icon = Icons.Filled.ContentCut,
                        iconDescription = stringResource(R.string.technique),
                        clickable = false
                    )
                    SectionHeader(
                        text = stringResource(R.string.request_progress),
                        Modifier
                            .align(Alignment.Start)
                            .padding(vertical = 8.dp)
                    )
                    request?.let {
                        ProgressCard(
                            order = 1,
                            status = when {
                                artisticEvaluation != null -> "FINISHED"
                                else -> "PENDING"
                            },
                            stepName = stringResource(R.string.specialist_selection),
                            clickable = false
                        )
                        if (artisticEvaluation != null) {
                            ProgressCard(
                                order = 2,
                                status = artisticEvaluation.status,
                                stepName = stringResource(R.string.artistic_evaluation),
                                onClicked = {
                                    navController.navigate(TecnisisScreen.ArtisticRequestEvaluation.name + "/${it.id}/false")
                                }
                            )
                        }
                        if (economicEvaluation != null) {
                            ProgressCard(
                                order = 3,
                                status = when (economicEvaluation.status) {
                                    "PENDING" -> "PENDING"
                                    else -> "FINISHED"
                                },
                                stepName = stringResource(R.string.economic_evaluation),
                                onClicked = {
                                    navController.navigate(TecnisisScreen.EconomicRequestEvaluation.name + "/${it.id}/false")
                                }
                            )
                        }
                    }
                }
            }

        }
        BottomPattern()
    }
}

@Preview(showBackground = true)
@Composable
fun ViewRequestScreenPreview() {
    TecnisisTheme {
    }
}