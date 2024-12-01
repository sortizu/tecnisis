package com.example.tecnisis.ui.components


import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.draw.clip
import com.example.tecnisis.R
import androidx.compose.material3.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.SquareFoot
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Popup
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

enum class TopBarState {
    EXPANDED, COLLAPSED
}

// Patron de la parte inferior de la pantalla
@Composable
fun BottomPattern() {
    val navigationBarPadding = WindowInsets.navigationBars.asPaddingValues()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(navigationBarPadding),
        contentAlignment = Alignment.BottomCenter
    ) {
        Image(
            painter = painterResource(id = R.drawable.pattern),
            contentDescription = "Bottom Pattern",
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(Color.White)
            ,contentScale = ContentScale.FillWidth
        )
    }
}

// "HorizontalDivider" que muestra el titulo de la interfaz
@Composable
fun ScreenTitle(modifier: Modifier = Modifier, text: String) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        HorizontalDivider(thickness = 1.dp)
        SectionHeader(text = text)
    }
}

@Composable
fun SectionHeader(text: String, modifier: Modifier = Modifier){
    Text(text = text,
        style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color.Gray),
        modifier = modifier)
}


@Composable
fun InfoBox(
    modifier: Modifier = Modifier,
    description: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            //.padding(horizontal = 20.dp, vertical = 10.dp)
            .background(
                color = Color(0xFFFFA726),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
            )
            .padding(vertical = 8.dp, horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = description,
            color = Color.Black,
            fontSize = 14.sp
        )
    }
}

@Composable
fun ImageCard(
    image: String,  // Replace with actual image resource ID
    title: String,
    date: String,
    dimensions: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .height(250.dp),
        color = Color.LightGray
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Background image with scaling

            if (image.isNotEmpty()) {
                val imageBytes = Base64.decode(image, Base64.DEFAULT)
                val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.media),
                    contentDescription = "Placeholder Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }


            // Bottom-left title text
            Text(
                text = title,
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(8.dp)
            )

            // Top-right date and dimensions labels
            Column(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            ) {
                LabelItem(text = date, icon = Icons.Filled.CalendarToday)
                Spacer(modifier = Modifier.height(4.dp))
                LabelItem(text = dimensions, icon = Icons.Filled.SquareFoot)
            }
        }
    }
}

@Composable
fun LabelItem(text: String, icon: ImageVector) { // Change icon type to ImageVector
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(Color(0xFFEFEFEF))
            .padding(horizontal = 4.dp, vertical = 2.dp)
    ) {
        Icon( // Use Icon composable instead of Image
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            color = Color.Black,
            fontSize = 12.sp
        )
    }
}

// Added from: https://gist.github.com/kirmartuk/a93fb5519ac44bb6170ce800d3b76ef8
@Composable
fun SingleChoiceDialog(
    title: String,
    radioOptions: List<String>,
    indexOfDefault: Int,
    isDialogVisible: (Boolean) -> Unit = {},
    onSelectedItem: (String) -> Unit = {},
    onSelectedItemIndex: (Int) -> Unit = {}
) {
    val (selectedItemIndex, setSelectedItemIndex) = remember {
        mutableStateOf(indexOfDefault)
    }
    AlertDialog(
        title = { Text(text = title) },
        text = {
            RadioItem(radioOptions, selectedItemIndex, setSelectedItemIndex)
        },
        onDismissRequest = {
            isDialogVisible(false)
        },
        dismissButton = {
            TextButton(onClick = { isDialogVisible(false) }) {
                Text(text = "Cancelar")
            }
        },
        confirmButton = {
            TextButton(onClick = {
                isDialogVisible(false)
                onSelectedItem(radioOptions[selectedItemIndex])
                onSelectedItemIndex(selectedItemIndex)
            }) {
                Text(text = "OK")
            }
        }
    )
}

@Composable
fun RadioItem(items: List<String>, selectedItemIndex: Int, setIndexOfSelected: (Int) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        items.forEachIndexed { index, text ->
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    setIndexOfSelected(index)
                }) {
                RadioButton(
                    selected = (index == selectedItemIndex),
                    onClick = {
                        setIndexOfSelected(index)
                    }
                )
                Text(
                    text = text
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TecnisisTopAppBar(
    state: TopBarState = TopBarState.COLLAPSED,
    modifier: Modifier = Modifier,
    onProfileClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {},
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "TECNISIS",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    tint = Color.Red,
                    modifier = Modifier.size(32.dp)
                )
            }
        },
        actions = {
            if (state == TopBarState.EXPANDED) {
                IconButton(onClick = onProfileClick) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Profile",
                        tint = Color.Black,
                        modifier = Modifier.size(32.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = onLogoutClick)
                {
                    Icon(
                        imageVector = Icons.Default.Logout,
                        contentDescription = "Logo",
                        tint = Color.Black,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Transparent),
        modifier = modifier
    )
}