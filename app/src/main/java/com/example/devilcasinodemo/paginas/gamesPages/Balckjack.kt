package com.example.devilcasinodemo.paginas.gamesPages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.devilcasinodemo.R
import com.example.devilcasinodemo.ui.theme.DevilCasinoDemoTheme

@Composable
fun BlackjackScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.board),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Buttons at bottom right
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter) // Position bottom-right
                .padding(20.dp)
            ,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                modifier = Modifier.
                width(200.dp),
                onClick = {  },
                shape = RoundedCornerShape(8.dp)

            ) {
                Text("Hit")
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {  },
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Stand")
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewBJ() {
    DevilCasinoDemoTheme {
        val navController = rememberNavController()
        BlackjackScreen(navController)
    }
}