package com.example.devilcasinodemo.paginas.lobby

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.devilcasinodemo.R
import com.example.devilcasinodemo.mvc.LoginViewModel

data class Game(
    val name: String,
    val imageRes: Int,
    val route: String
)

@Composable
fun Lobby(navController: NavHostController, loginViewModel: LoginViewModel) {
    val scrollState = rememberScrollState()
    val games = listOf(
        Game("Blackjack", R.drawable.blackjackimage, "blackjack"),
        Game("Liars Dice", R.drawable.dicegameimage, "liars_dice"),

    )
    val userId = loginViewModel.userId
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        NeonText(
            text = "Welcome to the Devil's Casino",
            color = Color(0xFFFFE082),
            fontSize = 30.sp
        )

        Spacer(modifier = Modifier.padding(20.dp))


        NeonText(
            text = userId?.toString() ?: "Unknown user",
            color = Color(0xFFFFE082),
            fontSize = 24.sp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState)
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            games.forEach { game ->
                GameItem(game = game) {
                    navController.navigate(game.route)
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        // Glowing blood-red pulsing text
        Text(
            text = "The devil is preparing more tricks, but for now you only get this sinner",
            color = Color.Red,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun GameItem(game: Game, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(width = 200.dp, height = 300.dp) // Adjust size as needed
            .shadow(8.dp, RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .background(Color.DarkGray, RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = game.imageRes),
            contentDescription = game.name,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
    }
}


@Composable
fun NeonText(
    text: String,
    color: Color,
    fontSize: TextUnit
) {
    Text(
        text = text,
        color = color,
        fontSize = fontSize,
        style = TextStyle(
            shadow = Shadow(
                color = color.copy(alpha = 0.9f),
                blurRadius = 20f
            )
        )
    )
}
