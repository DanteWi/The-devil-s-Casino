package com.example.devilcasinodemo.paginas.gamesPages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.devilcasinodemo.mvc.DevilDiceViewModel
import kotlinx.coroutines.delay
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun DevilDiceScreen(
    navController: NavHostController,
    viewModel: DevilDiceViewModel,
    userId: Long
) {
    var showPopup by remember { mutableStateOf(true) }

    if (showPopup) {
        Column (horizontalAlignment = Alignment.CenterHorizontally,
        ){
            AlertDialog(
                onDismissRequest = { },
                title = {
                    Text(
                        text = "The devil is working hard but it aint done yet siner",
                        color = Color.Red,
                        fontWeight = FontWeight.Bold,

                        fontSize = 18.sp
                    )
                },
                text = {
                    Text(
                        text = "This game is still under development , sorry for the incovinice",
                        color = Color.White,
                        fontSize = 12.sp
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showPopup = false
                            navController.navigate("lobby") {
                                popUpTo("liars_dice") { inclusive = true }
                            }
                        }
                    ) {
                        Text("Send back to lody")
                    }
                },
                containerColor = Color(0xFF121212)
            )
        }
    }

    LaunchedEffect(userId) {
        while(true){
            delay(1000)
            viewModel.refreshState(userId)
        }
    }
    val state = viewModel.gameState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

   ) {

        Text("Devil Dice", color = Color.Red, fontSize = 28.sp)

        Spacer(Modifier.height(20.dp))

        Text("Turn: ${state.currentTurn}")

        Text("Bet: ${state.currentBet}")

        Text("My Dice")

        Row {
            state.myDice.forEach {
                DiceBox(it)
            }
        }

        Spacer(Modifier.height(20.dp))

        Button(onClick = {
            viewModel.call(state.gameId, userId)
        }) {
            Text("CALL LIE")
        }
    }
}

@Composable
fun DiceBox(value: Int) {
    Box(
        modifier = Modifier
            .size(60.dp)
            .background(Color.White, RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            value.toString(),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
