package com.example.tecnisis.ui.view_request

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.tecnisis.TecnisisScreen
import com.example.tecnisis.ui.components.ScreenTitle
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.SquareFoot
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.tecnisis.R
import com.example.tecnisis.ui.components.HighlightButton
import com.example.tecnisis.ui.components.ImageCard
import com.example.tecnisis.ui.components.SectionHeader
import com.example.tecnisis.ui.components.SelectableListItem
import com.example.tecnisis.ui.theme.TecnisisTheme

@Composable
fun ViewRequestScreen(
    currentScreen: TecnisisScreen = TecnisisScreen.ArtisticRequestReview,
    modifier: Modifier = Modifier
){
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
            Modifier.align(Alignment.Start).padding(vertical = 8.dp)
        )

    }
}

@Preview(showBackground = true)
@Composable
fun ViewRequestScreenPreview() {
    TecnisisTheme {
        ViewRequestScreen()
    }
}