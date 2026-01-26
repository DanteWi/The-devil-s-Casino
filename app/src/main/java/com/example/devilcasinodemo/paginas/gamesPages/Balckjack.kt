package com.example.devilcasinodemo.paginas.gamesPages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.devilcasinodemo.R
import com.example.devilcasinodemo.mvc.BlackjackViewModel
import com.example.devilcasinodemo.mvc.dto.BlackjackState


@Composable
fun BlackjackScreen(
    navHostController: NavHostController,
    viewModel: BlackjackViewModel,
    userId: Long
) {
    var showEndGamePopup by remember { mutableStateOf(false) }

    var showBetDialog by remember { mutableStateOf(true) }
    var betAmount by remember { mutableStateOf("10") }
    var loading by remember { mutableStateOf(false) }

    val state = viewModel.gameState
    val canHit = state.playerTotal < 21 && !state.finished
    val canStand = !state.finished


    // For dealer animation
    var dealerVisible by remember { mutableStateOf(false) }
    var dealerCardsAnimated by remember { mutableStateOf(listOf<String>()) }

    // Start game
    fun startGame() {
        val bet = betAmount.toDoubleOrNull() ?: return
        loading = true
        dealerVisible = false
        dealerCardsAnimated = emptyList()
        viewModel.startGame(userId, bet)
        showBetDialog = false
    }

    // BET POPUP
    if (showBetDialog) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = {},
            containerColor = Color(0xFF111111),
            shape = RoundedCornerShape(20.dp),

            title = {
                Text(
                    text = "PLACE YOUR BET",
                    color = Color.Red,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            },

            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Enter your bet amount",
                        color = Color.White,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    androidx.compose.material3.OutlinedTextField(
                        value = betAmount,
                        onValueChange = { betAmount = it },
                        singleLine = true,
                        textStyle = LocalTextStyle.current.copy(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        label = { Text("Bet Amount", fontSize = 14.sp) }
                    )
                }
            },

            confirmButton = {
                Button(
                    onClick = { startGame() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text(
                        "START GAME",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        )
    }

    // MAIN GAME
    if (!showBetDialog ) {

        // Animate dealer after player stands
        LaunchedEffect(state.finished) {
            if (state.finished && state.status != "PLAYER_BUST") {
                dealerVisible = true
                dealerCardsAnimated = emptyList()

                // Simulate dealer drawing cards one by one
                state.dealerCards.forEachIndexed { index, card ->
                    dealerCardsAnimated = state.dealerCards.take(index + 1)
                    kotlinx.coroutines.delay(800) // 0.8 seconds per card
                }
            } else if (state.status == "PLAYER_BUST") {
                dealerVisible = false
            }
        }

        BlackjackTable(
            state = state.copy(
                dealerCards = if (dealerVisible) dealerCardsAnimated else emptyList()
            ),
            onHit = {
                if (canHit && !loading) {
                    loading = true
                    viewModel.hit(userId)
                }
            },
            onStand = {
                if (canStand && !loading) {
                    loading = true
                    viewModel.stand(userId)
                }
            },
            onChipClick = { chip -> betAmount = (betAmount.toInt() + chip).toString() }
        )
    }

    // END GAME
    LaunchedEffect(state?.status) {
        if (state?.status == "PLAYER_BUST" || state?.finished == true) {
            // Wait a bit before showing popup (e.g., 1 second)
            kotlinx.coroutines.delay(5000)
            showEndGamePopup = true
        } else {
            showEndGamePopup = false
        }
    }

    if (showEndGamePopup) {
        val isWin = state?.status == "PLAYER_WIN" || state?.status == "PUSH"

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.9f)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .background(
                        Color(0xFF111111),
                        shape = RoundedCornerShape(24.dp)
                    )
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // RESULT IMAGE
                Image(
                    painter = painterResource(
                        id = if (isWin) R.drawable.imagewinner else R.drawable.imageloser
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(260.dp)
                        .padding(bottom = 16.dp),
                    contentScale = ContentScale.Fit
                )

                Text(
                    text = if (isWin) "YOU WIN!" else "YOU LOST",
                    color = if (isWin) Color(0xFFFFD700) else Color.Red,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(modifier = Modifier.height(8.dp))

                if (state != null) {
                    Text(
                        text = when (state.status) {
                            "PUSH" -> "It's a push!"
                            "PLAYER_WIN" -> "You win, for now!😈"
                            else -> "Dealer wins!"
                        },
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (state != null) {
                    Text(
                        text = "Bet: ${state.bet}",
                        color = if (isWin) Color(0xFFFFD700) else Color.Red,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = {
                            showBetDialog = true
                            viewModel.resetGame()
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text("PLAY AGAIN", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = {
                            navHostController.navigate("lobby") {
                                popUpTo("blackjack") { inclusive = true }
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text("LOBBY", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }


    // Reset loading when state changes
    LaunchedEffect(state) {
        loading = false
    }
}


fun getCardRes(card: String): Int {
    return when(card) {
       // "AH" -> R.drawable.ah
       // "2H" -> R.drawable._2h
        // Add all 52 cards
        else -> R.drawable.cardback
    }
}
@Composable
fun CardPlaceholder(card: String, width: Dp = 100.dp, height: Dp = 150.dp) {
    Box(
        modifier = Modifier
            .size(width, height)
            .background(Color.Gray, shape = RoundedCornerShape(12.dp))
            .border(1.dp, Color.Black, shape = RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = card,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
    }
}
@Composable
fun BlackjackTable(
    state: BlackjackState,
    onHit: () -> Unit,
    onStand: () -> Unit,
    onChipClick: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.9f)
                .background(
                    Color(0xFFB11226),
                    RoundedCornerShape(40.dp)
                )
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // DEALER CARDS (TOP)
            CardRow(state.dealerCards)

            // DEVIL CENTER
            Image(
                painter = painterResource(R.drawable.devilstick),
                contentDescription = null,
                modifier = Modifier.size(120.dp)
            )

            // PLAYER CARDS (BOTTOM)
            CardRow(state.playerCards)

            // CHIPS
            ChipRow(onChipClick)

            // HIT / STAND
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CasinoButton("HIT", onHit)
                CasinoButton("STAND", onStand)
            }
        }
    }
}
@Composable
fun CardRow(cards: List<String>) {
    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (card in cards) {
            CardPlaceholder(card = card, width = 100.dp, height = 150.dp)
        }
    }
}


@Composable
fun ChipRow(onChipClick: (Int) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        Chip(R.drawable.jet1, 1, onChipClick)
        Chip(R.drawable.jet5, 5, onChipClick)
        Chip(R.drawable.jet10, 10, onChipClick)
        Chip(R.drawable.jet50, 50, onChipClick)
        Chip(R.drawable.jet100, 100, onChipClick)
    }
}

@Composable
fun Chip(drawable: Int, value: Int, onClick: (Int) -> Unit) {
    Image(
        painter = painterResource(drawable),
        contentDescription = null,
        modifier = Modifier
            .size(64.dp)
            .clickable { onClick(value) }
    )
}
@Composable
fun CasinoButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(140.dp)
            .height(52.dp),
        shape = RoundedCornerShape(26.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFF9933)
        )
    ) {
        Text(
            text = text,
            color = Color.Red,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}
@Composable
fun EmptyCardSlot() {
    Box(
        modifier = Modifier
            .size(60.dp, 90.dp)
            .background(Color.Black, RoundedCornerShape(8.dp))
    )
}
