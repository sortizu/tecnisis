package com.example.tecnisis.ui.dashboard

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tecnisis.TecnisisScreen
import com.example.tecnisis.config.datastore.DataStoreManager
import com.example.tecnisis.data.request.RequestResponse
import com.example.tecnisis.ui.components.CustomDatePickerField
import com.example.tecnisis.ui.components.CustomSingleChoiceSegmentedButton
import com.example.tecnisis.ui.components.RequestCard
import com.example.tecnisis.ui.components.ScreenTitle
import com.example.tecnisis.ui.dashboard.DashboardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    currentScreen: TecnisisScreen = TecnisisScreen.Dashboard,
    modifier: Modifier = Modifier,
    navController: NavController,
    enableFloatingActionButton: MutableState<Boolean>,
    floatingButtonPressed: MutableState<() -> Unit>
) {
    // Collect the UI state from the ViewModel
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val requests = uiState.requests
    val dataStoreManager = remember { DataStoreManager(context) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp)
            .background(color = Color.Transparent)
    ) {
        // Search bar at the top
        ScreenTitle(text = context.getString(currentScreen.title))
        Spacer(modifier = Modifier.height(8.dp))
        CustomDatePickerField(
            label = "Fecha Inicial",
            defaultDate = uiState.initalDate,
            onDateSelected = { viewModel.updateInitialDate(it) }
        )
        Spacer(modifier = Modifier.height(8.dp))
        CustomDatePickerField(
            label = "Fecha Final",
            defaultDate = uiState.finalDate,
            onDateSelected = { viewModel.updateFinalDate(it) }
        )
        Spacer(modifier = Modifier.height(8.dp))
        // Dashboard
        BarGraph(requests = viewModel.getRequestBetweenDates())
    }
}

@Composable
fun BarGraph(requests: List<RequestResponse>) {
    val requestsPerDay = requests.groupBy { it.date }.mapValues { it.value.size }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        requestsPerDay.forEach { (date, count) ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)

            ) {
                Text(
                    text = date,
                    fontSize = 14.sp,
                    modifier = Modifier.width(80.dp)
                )
                Box(
                    modifier = Modifier
                        .height(20.dp)
                        .weight(1f)
                        .background(Color.Yellow, RoundedCornerShape(4.dp))
                ){
                    Box(modifier = Modifier.fillMaxHeight().fillMaxWidth(count.toFloat() / requests.size)
                        .background(Color.Red, RoundedCornerShape(4.dp)))
                }
                Text(
                    text = count.toString(),
                    fontSize = 14.sp,
                    modifier = Modifier.width(40.dp)
                )
            }
        }
    }
}