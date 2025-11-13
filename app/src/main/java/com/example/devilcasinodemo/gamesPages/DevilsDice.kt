package com.example.devilcasinodemo.gamesPages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.devilcasinodemo.R
import com.example.devilcasinodemo.ui.theme.DevilCasinoDemoTheme

@Composable
fun DevilDicesScreen() {
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

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDD() {
    DevilCasinoDemoTheme {
        DevilDicesScreen()
    }
}