package com.example.devilcasinodemo.paginas.gamesPages

import WalletViewModel
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
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.devilcasinodemo.R
import com.example.devilcasinodemo.mvc.BlackjackViewModel
import com.example.devilcasinodemo.mvc.dto.BlackjackState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.draw.rotate


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlackjackScreen(
    navHostController: NavHostController,
    viewModel: BlackjackViewModel,
    userId: Long,
    walletViewModel: WalletViewModel
) {
    var showEndGamePopup by remember { mutableStateOf(false) }

    var showBetDialog by remember { mutableStateOf(true) }
    var betAmount by remember { mutableStateOf("10") }
    var loading by remember { mutableStateOf(false) }

    val state = viewModel.gameState
    val canHit = state.playerTotal < 21 && !state.finished
    val canStand = !state.finished
    val bet = betAmount.toDoubleOrNull() ?: 0.0
    val isBetValid by remember(bet, walletViewModel.walletAmount) {
        derivedStateOf {
            bet > 0 && bet <= walletViewModel.walletAmount
        }
    }
    val neonOrange = Color(0xFFFF9500)
    val backgroundColor = Color(0xFF0C0602)

    // For dealer animation
    var dealerVisible by remember { mutableStateOf(false) }
    var dealerCardsAnimated by remember { mutableStateOf(listOf<String>()) }

    LaunchedEffect(Unit) {
        dealerVisible = false
        dealerCardsAnimated = emptyList()
    }
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
                    text = stringResource(R.string.place_your_bet),
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
                        text = stringResource(R.string.enter_your_bet_amount),
                        color = neonOrange,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

// Bet Amount Field
                    androidx.compose.material3.OutlinedTextField(
                        value = betAmount,
                        onValueChange = { betAmount = it },
                        singleLine = true,
                        textStyle = LocalTextStyle.current.copy(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = neonOrange // text color
                        ),
                        label = { Text(stringResource(R.string.bet_amount), color = neonOrange) },
                        colors = androidx.compose.material3.TextFieldDefaults.outlinedTextFieldColors(

                            focusedBorderColor = neonOrange,
                            unfocusedBorderColor = neonOrange,
                            cursorColor = neonOrange,
                            containerColor = backgroundColor, // text field background
                            errorBorderColor = Color.Red
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                    )

                }
            },

            confirmButton = {
                Button(
                    onClick = { startGame() },
                    enabled = isBetValid, // <-- disables button if bet invalid
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text(
                        stringResource(R.string.start_game),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isBetValid) Color.Red else Color.Red // optional: indicate disabled
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
            canHit = canHit && !loading,
            canStand = canStand && !loading
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
                    text = if (isWin) stringResource(R.string.you_win) else stringResource(R.string.you_lost),
                    color = if (isWin) Color(0xFFFFD700) else Color.Red,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(modifier = Modifier.height(8.dp))

                if (state != null) {
                    Text(
                        text = when (state.status) {
                            "PUSH" -> stringResource(R.string.it_s_a_push)
                            "PLAYER_WIN" -> stringResource(R.string.you_win_for_now)
                            else -> stringResource(R.string.dealer_wins)
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
                            showEndGamePopup = false
                            dealerVisible = false
                            dealerCardsAnimated = emptyList()
                            viewModel.resetGame()
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text(stringResource(R.string.play_again), fontSize = 16.sp, color = Color.Red)
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
                        Text("LOBBY", fontSize = 16.sp, color = Color.Red)
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
    canHit: Boolean,
    canStand: Boolean
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
                .background(Color(0xFFB11226), RoundedCornerShape(40.dp))
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // DEALER CARDS
            CardRow(state.dealerCards)

            // CENTER IMAGE
            Image(
                painter = painterResource(R.drawable.devilstick),
                contentDescription = null,
                modifier = Modifier.size(120.dp)
            )

            // PLAYER CARDS
            CardRow(state.playerCards)

            // HIT / STAND BUTTONS
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CasinoButton("HIT", onHit, enabled = canHit)
                CasinoButton("STAND", onStand, enabled = canStand)
            }
        }
    }
}


@Composable
fun CardRow(cards: List<String>) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.horizontalScroll(rememberScrollState())
    ) {
        for (card in cards) {
            CardComposable(card)
        }
    }
}



@Composable
fun CasinoButton(text: String, onClick: () -> Unit, enabled: Boolean = true) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier.width(140.dp).height(52.dp),
        shape = RoundedCornerShape(26.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if(enabled) Color(0xFFFF9933) else Color.Gray
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

fun suitSymbol(suit: Char): String = when(suit) {
    'H' -> "♥"
    'D' -> "♦"
    'C' -> "♣"
    'S' -> "♠"
    else -> "?"
}
fun getPipLayout(rank: String): List<List<Boolean>> = when(rank) {
    "A" -> listOf(listOf(true)) // single pip in the middle
    "2" -> listOf(listOf(true), listOf(true))
    "3" -> listOf(listOf(true), listOf(true), listOf(true))
    "4" -> listOf(listOf(true, true), listOf(true, true))
    "5" -> listOf(listOf(true, true), listOf(true), listOf(true, true))
    "6" -> listOf(listOf(true, true, true), listOf(true, true, true))
    "7" -> listOf(listOf(true, true, true), listOf(true), listOf(true, true, true))
    "8" -> listOf(listOf(true, true, true), listOf(true, true), listOf(true, true, true))
    "9" -> listOf(listOf(true, true, true), listOf(true, true, true), listOf(true, true, true))
    "10" -> listOf(listOf(true, true, true, true), listOf(true, true, true, true), listOf(true, true))
    else -> listOf() // face cards will have their own rendering
}
@Composable
fun CardComposable(card: String, width: Dp = 100.dp, height: Dp = 150.dp) {
    val rank = card.dropLast(1)
    val suit = card.last()
    val symbol = suitSymbol(suit)

    Box(
        modifier = Modifier
            .size(width, height)
            .background(Color.White, RoundedCornerShape(12.dp))
            .border(2.dp, Color.Black, RoundedCornerShape(12.dp))
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top-left rank & suit
            Text("$rank$symbol", color = if (suit == 'H' || suit == 'D') Color.Red else Color.Black)

            // Pips (for numbered cards)
            val pipLayout = getPipLayout(rank)
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                for (row in pipLayout) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        for (pip in row) {
                            if (pip) {
                                Text(symbol, color = if (suit == 'H' || suit == 'D') Color.Red else Color.Black, fontSize = 20.sp)
                            } else {
                                Spacer(modifier = Modifier.width(20.dp))
                            }
                        }
                    }
                }
            }

            // Bottom-right rank & suit (rotated)
            Text(
                "$rank$symbol",
                color = if (suit == 'H' || suit == 'D') Color.Red else Color.Black,
                modifier = Modifier.rotate(180f)
            )
        }
    }
}
