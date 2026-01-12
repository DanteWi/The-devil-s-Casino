package com.example.devilcasinodemo.paginas.lobby

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
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
    val username = loginViewModel.username

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Welcome text
        Spacer(Modifier.height(20.dp))
        NeonText(
            text = "Welcome to the Devil's Casino",
            color = Color(0xFFFFE082),
            fontSize = 28.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        // User ID
        NeonText(
            text = username ?: "Unknown user",
            color = Color(0xFFFFE082),
            fontSize = 22.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Scrollable game list
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            games.forEach { game ->
                GameItem(game = game) {
                    navController.navigate(game.route)
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Footer text
        Text(
            text = "The devil is preparing more tricks, but for now you only get this sinner",
            color = Color.Red,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        )
    }
}

@Composable
fun GameItem(game: Game, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .widthIn(min = 120.dp, max = 180.dp) // limit width
            .heightIn(min = 180.dp, max = 250.dp) // limit height
            .shadow(8.dp, RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .background(Color.DarkGray, RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = game.imageRes),
            contentDescription = game.name,
            contentScale = ContentScale.Crop,
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
        ),
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )
}
