package com.example.devilcasinodemo.paginas.lobby


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.devilcasinodemo.R
import com.example.devilcasinodemo.music.MusicManager

@Composable
fun SettingsScreen() {

    // States
    var musicVolume by remember { mutableStateOf(0.5f) }
    var isMuted by remember { mutableStateOf(false) }
    var selectedLanguage by remember { mutableStateOf("EN") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // ===== TITLE =====
        Spacer(Modifier.height(20.dp))

        Text(
            text = "Settings",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFF1414),
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(40.dp))

        // ===== MUSIC VOLUME =====

        Text(
            text = stringResource(R.string.music_volume),
            color = Color.Red,
            fontSize = 20.sp
        )

        Spacer(Modifier.height(8.dp))
        Slider(
            value = musicVolume,
            onValueChange = {
                musicVolume = it
                isMuted = false
                MusicManager.setVolume(it)
                MusicManager.mute(false)
            },
            valueRange = 0f..1f,
            modifier = Modifier.fillMaxWidth(0.9f)
        )

        Button(
            onClick = {
                isMuted = !isMuted
                MusicManager.mute(isMuted)
                if (isMuted) musicVolume = 0f
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isMuted) Color.Red else Color(0xFFDE5D02)
            ),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(50.dp)
        ) {
            Text(
                text = if (isMuted) stringResource(R.string.music_muted) else stringResource(R.string.mute_music),
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }


        Spacer(Modifier.height(40.dp))

        // ===== LANGUAGE TITLE =====

        Text(
            text = "Language",
            color = Color.Red,
            fontSize = 20.sp
        )

        Spacer(Modifier.height(16.dp))

        // ===== FLAG BUTTONS =====

        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            LanguageButton(
                flag = R.drawable.flag_uk,
                isSelected = selectedLanguage == "EN"
            ) {
                selectedLanguage = "EN"
            }

            LanguageButton(
                flag = R.drawable.flag_es,
                isSelected = selectedLanguage == "ES"
            ) {
                selectedLanguage = "ES"
            }

            LanguageButton(
                flag = R.drawable.flag_ro,
                isSelected = selectedLanguage == "RO"
            ) {
                selectedLanguage = "RO"
            }
        }

        Spacer(Modifier.height(30.dp))

        Text(
            text = "Selected: $selectedLanguage",
            color = Color.White,
            fontSize = 16.sp
        )
    }
}
@Composable
fun LanguageButton(
    flag: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(70.dp)
            .shadow(
                if (isSelected) 10.dp else 2.dp,
                RoundedCornerShape(12.dp)
            )
            .background(
                if (isSelected) Color(0xFFFFE082) else Color.DarkGray,
                RoundedCornerShape(12.dp)
            )
            .clickable { onClick() },

        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painterResource(id = flag),
            contentDescription = stringResource(R.string.language_flag),
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp)
        )
    }
}
