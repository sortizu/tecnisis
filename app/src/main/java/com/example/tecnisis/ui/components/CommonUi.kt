package com.example.tecnisis.ui.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.TextFieldValue
import com.example.tecnisis.R
import androidx.compose.material3.*
import com.example.tecnisis.data.Solicitud
import com.example.tecnisis.data.SolicitudWithObra

// "HorizontalDivider" que muestra el titulo de la interfaz
@Composable
fun ScreenTitle(modifier: Modifier = Modifier, text: String) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        HorizontalDivider(thickness = 2.dp)
        Text(text = text, style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold))
    }
}

@Composable
fun RequestList(solicitudes: List<SolicitudWithObra>) {
    LazyColumn {
        items(solicitudes.size) { index ->
            RequestCard(index+1,solicitudes[index])
        }
    }
}

@Composable
fun RequestCard(orden: Int, solicitudWithObra: SolicitudWithObra) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            // Aplica padding horizontal solo a la izquierda
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
                Text(text = orden.toString(), color = Color.White)
            }

            // Detalles de la solicitud
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                Text(text = solicitudWithObra.obra.titulo , fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(text = solicitudWithObra.solicitud.estado , fontSize = 14.sp, color = Color.Gray)
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