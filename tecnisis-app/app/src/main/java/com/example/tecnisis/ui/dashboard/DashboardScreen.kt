package com.example.tecnisis.ui.dashboard

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tecnisis.TecnisisScreen
import com.example.tecnisis.config.datastore.DataStoreManager
import com.example.tecnisis.data.request.RequestResponse
import com.example.tecnisis.ui.components.CustomDatePickerField
import com.example.tecnisis.ui.components.ScreenTitle
import com.example.tecnisis.ui.components.SectionHeader
import com.github.tehras.charts.bar.BarChart
import com.github.tehras.charts.bar.BarChartData
import com.github.tehras.charts.bar.BarChartData.Bar
import com.github.tehras.charts.bar.renderer.label.LabelDrawer
import com.github.tehras.charts.bar.renderer.label.SimpleValueDrawer
import com.github.tehras.charts.bar.renderer.xaxis.SimpleXAxisDrawer
import com.github.tehras.charts.bar.renderer.yaxis.SimpleYAxisDrawer
import com.github.tehras.charts.piechart.PieChart
import com.github.tehras.charts.piechart.PieChartData
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

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
        Spacer(modifier = Modifier.height(16.dp))
        SectionHeader(text = "Distribucion de obras por técnica")
        Spacer(modifier = Modifier.height(16.dp))
        TechniquePieChart(techniqueDistribution = viewModel.getTechniqueDistribution())
        Spacer(modifier = Modifier.height(100.dp))
    }
}

@Composable
fun BarGraph(requests: List<RequestResponse>) {
    val requestsPerDay = requests.groupBy { it.date }.mapValues { it.value.size }
    val bars = requestsPerDay.map { (date, count) ->
        Bar(
            label = date,
            value = count.toFloat(),
            color = Color.Blue
        )
    }

    val barChartData = BarChartData(
        bars = bars,
        padBy = 2f
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        BarChart(
            barChartData = barChartData,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            labelDrawer = SimpleValueDrawer(),
            xAxisDrawer = SimpleXAxisDrawer(),
            yAxisDrawer = SimpleYAxisDrawer(
                labelValueFormatter = { value -> value.toInt().toString() }
            ),
            /*labelDrawer = LabelDrawer(
                labelTextSize = 14.sp,
                labelTextColor = Color.Black
            )*/
        )
    }
}

@Composable
fun TechniquePieChart(techniqueDistribution: Map<String, Int>) {
    val slices = techniqueDistribution.map { (technique, count) ->
        PieChartData.Slice(
            value = count.toFloat(),
            color = Color(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256)),
        )
    }

    val pieChartData = PieChartData(
        slices = slices
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        PieChart(
            pieChartData = pieChartData,
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
        )
    }
}