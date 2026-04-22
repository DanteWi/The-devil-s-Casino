package com.example.devilcasinodemo.paginas.gamesPages

import androidx.compose.foundation.background
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

@Composable
fun DevilDiceScreen(
    navController: NavHostController,
    viewModel: DevilDiceViewModel,
    userId: Long
) {
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
        horizontalAlignment = Alignment.CenterHorizontally
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
