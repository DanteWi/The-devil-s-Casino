package com.example.devilcasinodemo.paginas.lobby

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

// ------------------------------------------------------
// DATA CLASSES
// ------------------------------------------------------

data class CardItem(val name: String, val type: String)

// ------------------------------------------------------
// MAIN USER SCREEN
// ------------------------------------------------------

@Composable
fun User(navController: NavHostController) {

    val winPercent = 32.0
    val losePercent = 20.0

    // ---------- CARD PICKER STATE ----------
    var showCardDialog by remember { mutableStateOf(false) }

    var cards by remember {
        mutableStateOf(
            listOf(
                CardItem("ES149185...", "master"),
                CardItem("ES992311...", "visa"),
                CardItem("PayPal Account", "paypal")
            )
        )
    }

    var selectedCard by remember { mutableStateOf(cards.first()) }

    // ------------------------------------------------------
    // SCREEN LAYOUT
    // ------------------------------------------------------

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Avatar + Settings
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .background(Color(0xFFB71C1C), RoundedCornerShape(24.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(90.dp)
                )
            }

            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.Transparent, RoundedCornerShape(24.dp)),
                contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(90.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Text("(USER NAME)", color = Color.Yellow, fontSize = 26.sp)
        Text("XXXX   DC", color = Color(0xFFFF1744), fontSize = 26.sp)

        Spacer(modifier = Modifier.height(40.dp))

        // DONUT CHART
        DonutChart(wins = winPercent, losses = losePercent, size = 250.dp)

        Spacer(modifier = Modifier.height(40.dp))

        // ------------------------------------------------------
        // CARD BOX (CLICK → OPENS POPUP)
        // ------------------------------------------------------

        Box(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .height(90.dp)
                .border(3.dp, Color.Red, RoundedCornerShape(20.dp))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showCardDialog = true }
            ) {

                CardLogo(type = selectedCard.type)

                Spacer(Modifier.width(10.dp))

                Text(
                    "Card in usage:   ${selectedCard.name}",
                    color = Color.White
                )
            }
        }
    }

    // ------------------------------------------------------
    // POPUP DIALOG FOR CARD PICKER
    // ------------------------------------------------------

    if (showCardDialog) {
        CardPickerDialog(
            cards = cards,
            onSelect = {
                selectedCard = it
                showCardDialog = false
            },
            onAdd = {
                val newCard = CardItem("NewCard${cards.size + 1}", "visa")
                cards = cards + newCard
                selectedCard = newCard
                showCardDialog = false
            },
            onDismiss = { showCardDialog = false }
        )
    }
}

// ------------------------------------------------------
// DONUT CHART (unchanged)
// ------------------------------------------------------

@Composable
fun DonutChart(wins: Double, losses: Double, size: Dp) {
    val sweepWin = (wins / (wins + losses)) * 360
    val sweepLoss = 360 - sweepWin

    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(size)) {
        Canvas(modifier = Modifier.size(size)) {
            val strokeWidth = size.toPx() * 0.20f

            drawArc(
                color = Color.Red,
                startAngle = -90f,
                sweepAngle = sweepLoss.toFloat(),
                useCenter = false,
                style = Stroke(width = strokeWidth)
            )

            drawArc(
                color = Color.Yellow,
                startAngle = (-90 + sweepLoss).toFloat(),
                sweepAngle = sweepWin.toFloat(),
                useCenter = false,
                style = Stroke(width = strokeWidth)
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Wins: $wins%", color = Color.White, fontSize = 14.sp)
            Text("Losses: $losses%", color = Color.White, fontSize = 14.sp)
        }
    }
}

// ------------------------------------------------------
// CARD LOGO (simple version)
// ------------------------------------------------------

@Composable
fun CardLogo(type: String) {
    val color = when (type.lowercase()) {
        "visa" -> Color.Blue
        "master" -> Color.Red
        "paypal" -> Color.Cyan
        else -> Color.Gray
    }

    Box(
        modifier = Modifier
            .size(26.dp)
            .background(color, CircleShape)
    )
}

// ------------------------------------------------------
// POPUP DIALOG
// ------------------------------------------------------

@Composable
fun CardPickerDialog(
    cards: List<CardItem>,
    onSelect: (CardItem) -> Unit,
    onAdd: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Card") },
        text = {
            Column {

                cards.forEach { card ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable { onSelect(card) },
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        CardLogo(type = card.type)

                        Spacer(Modifier.width(12.dp))

                        Text(card.name)
                    }
                }

                Spacer(Modifier.height(12.dp))

                Button(onClick = onAdd) {
                    Text("Add New Card")
                }
            }
        },
        confirmButton = {},
        dismissButton = {}
    )
}
