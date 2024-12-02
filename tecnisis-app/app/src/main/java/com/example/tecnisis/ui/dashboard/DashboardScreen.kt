package com.example.tecnisis.ui.dashboard

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.example.tecnisis.ui.components.BottomPattern
import com.example.tecnisis.ui.components.CustomDatePickerField
import com.example.tecnisis.ui.components.ScreenTitle
import com.example.tecnisis.ui.components.SectionHeader
import com.example.tecnisis.ui.components.TecnisisTopAppBar
import com.example.tecnisis.ui.components.TopBarState
import com.github.tehras.charts.bar.BarChart
import com.github.tehras.charts.bar.BarChartData
import com.github.tehras.charts.bar.BarChartData.Bar
import com.github.tehras.charts.bar.renderer.label.SimpleValueDrawer
import com.github.tehras.charts.bar.renderer.xaxis.SimpleXAxisDrawer
import com.github.tehras.charts.bar.renderer.yaxis.SimpleYAxisDrawer
import com.github.tehras.charts.piechart.PieChart
import com.github.tehras.charts.piechart.PieChartData
import com.github.tehras.charts.piechart.renderer.SimpleSliceDrawer
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
    floatingButton: MutableState<@Composable () -> Unit>,
    topAppBar: MutableState<@Composable () -> Unit>
) {
    // Collect the UI state from the ViewModel
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val requests = uiState.requests
    val dataStoreManager = remember { DataStoreManager(context) }

    topAppBar.value = {
        TecnisisTopAppBar(
            state = TopBarState.EXPANDED,
            onLogoutClick = {
                navController.navigate(TecnisisScreen.Login.name)
            },
            onProfileClick = {
                navController.navigate(TecnisisScreen.Profile.name + "/-1L/false")
            }
        )
    }
    floatingButton.value = {}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(color = Color.Transparent)
    ) {
        // Search bar at the top
        Column(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
        ){
            ScreenTitle(text = context.getString(currentScreen.title))
            Spacer(modifier = Modifier.height(24.dp))
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
            Spacer(modifier = Modifier.height(8.dp))
            val techniqueDistribution = viewModel.getTechniqueDistribution()
            val colors = techniqueDistribution.keys.map { Color(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256)) }
            TechniqueLegend(techniqueDistribution = techniqueDistribution, colors = colors)
            Spacer(modifier = Modifier.height(16.dp))
            TechniquePieChart(techniqueDistribution = techniqueDistribution, colors = colors)
        }

        BottomPattern()
    }
}

@Composable
fun BarGraph(requests: List<RequestResponse>) {
    val requestsPerDay = requests.groupBy { it.date }.mapValues { it.value.size }
    val bars = requestsPerDay.map { (date, count) ->
        Bar(
            label = date,
            value = count.toFloat(),
            color = Color.Red
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
                .height(200.dp),
            labelDrawer = SimpleValueDrawer(
                drawLocation = SimpleValueDrawer.DrawLocation.XAxis,
            ),
            xAxisDrawer = SimpleXAxisDrawer(),
            yAxisDrawer = SimpleYAxisDrawer(
                labelValueFormatter = { value -> value.toInt().toString() }
            )
        )
    }
}

@Composable
fun TechniqueLegend(techniqueDistribution: Map<String, Int>, colors: List<Color>) {
    Column(modifier = Modifier.padding(16.dp)) {
        techniqueDistribution.keys.forEachIndexed { index, technique ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(colors[index])
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = technique)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "(" + techniqueDistribution[technique].toString() + ")", fontSize = 12.sp)
                Spacer(modifier = Modifier.width(8.dp))
                if (techniqueDistribution.size > 1) {
                    val total = techniqueDistribution.values.sum().toFloat()
                    val percentage = (techniqueDistribution[technique]!!.toFloat() / total) * 100
                    Text(text = "%.2f%%".format(percentage), fontSize = 12.sp)
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
fun TechniquePieChart(techniqueDistribution: Map<String, Int>, colors: List<Color>) {
    val slices = techniqueDistribution.entries.mapIndexed { index, entry ->
        PieChartData.Slice(
            value = entry.value.toFloat(),
            color = colors[index],
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
                .height(200.dp)
        )
    }
}