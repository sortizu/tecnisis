package com.example.tecnisis.ui.artistic_request_review

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.tecnisis.R
import com.example.tecnisis.TecnisisScreen
import com.example.tecnisis.ui.components.ScreenTitle
import com.example.tecnisis.ui.components.SelectableListItem

@Composable
fun ArtisticRequestReviewScreen(
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
        // Image upload area
        ImageUploadSection()

        // Input fields
        SelectableListItem(
            text = "Artista: User 1",
            icon = Icons.Default.Person,
            iconDescription = ""
        )
        SelectableListItem(
            text = "Técnica: Tecnica 1",
            icon = Icons.Default.ContentCut,
            iconDescription = ""
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {  },
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFE2DD),
                contentColor = Color(0xFFA50007)
            ),
        ) {
            Text(text = "Aceptar")
        }
        Button(
            onClick = { },
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color(0xFFA50007)
            ),
        ) {
            Text(text = "Rechazar")
        }
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