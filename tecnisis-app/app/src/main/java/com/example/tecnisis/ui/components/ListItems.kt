package com.example.tecnisis.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tecnisis.R
import com.example.tecnisis.data.request.RequestResponse

@Composable
fun ProgressCard(order: Int, status: String, stepName: String, onClicked: () -> Unit = { }) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClicked() }
    ){
        ListItem(
            headlineContent = {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color.Red),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = order.toString(), color = Color.White)
                }
            },
            leadingContent = {
                Column {
                    Text(text = status, fontSize = 14.sp, color = Color.Gray)
                    Text(text = stepName, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            },
            trailingContent = {
                if (onClicked != { }){
                    Icon(
                        Icons.Default.ArrowRight,
                        contentDescription = "View Details"
                    )
                }
            }
        )
        HorizontalDivider()
    }
}

@Composable
fun RequestCard(order: Int, request: RequestResponse, onCardClick: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCardClick(request.id.toInt()) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 0.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Priority Circle
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(Color.Red),
                contentAlignment = Alignment.Center
            ) {
                Text(text = order.toString(), color = Color.White)
            }

            // Request details
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                Text(text = request.artWork.title, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(text = request.status, fontSize = 14.sp, color = Color.Gray)
            }
            // Imagen de la obra
            Image(
                painter = painterResource(id = R.drawable.media),
                contentDescription = "Obra",
                modifier = Modifier.size(100.dp)
            )
        }
    }
}

@Composable
fun SelectableListItem(
    text: String,
    icon: ImageVector,
    iconDescription: String,
    onClick: () -> Unit = {}
) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ){
        ListItem(
            headlineContent = { Text(text) },
            leadingContent = {
                Icon(
                    icon,
                    contentDescription = iconDescription,
                )
            },
            trailingContent = {
                Icon(
                    Icons.Default.ArrowRight,
                    contentDescription = "Open Modal"
                )
            }
        )
        HorizontalDivider()
    }
}
