package com.example.devilcasinodemo.paginas.lobby


import android.app.Activity
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.devilcasinodemo.R
import com.example.devilcasinodemo.lenguas.LocaleHelper
import com.example.devilcasinodemo.music.MusicManager

@Composable
fun SettingsScreen() {

    // States
    var musicVolume by remember { mutableStateOf(0.5f) }
    var isMuted by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val activity = context as Activity

    val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    var selectedLanguage by remember {
        mutableStateOf(
            prefs.getString("language", "en") ?: "en"
        )
    }


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
            text = stringResource(R.string.settings),
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
                isSelected = selectedLanguage == "en"
            ) {
                changeLanguage("en", context, activity) {
                    selectedLanguage = "en"
                }
            }

            LanguageButton(
                flag = R.drawable.flag_es,
                isSelected = selectedLanguage == "es"
            ) {
                changeLanguage("es", context, activity) {
                    selectedLanguage = "es"
                }
            }

            LanguageButton(
                flag = R.drawable.flag_ro,
                isSelected = selectedLanguage == "ro"
            ) {
                changeLanguage("ro", context, activity) {
                    selectedLanguage = "ro"
                }
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
fun changeLanguage(
    lang: String,
    context: Context,
    activity: Activity,
    onDone: () -> Unit
) {
    // Save user preference
    val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
    prefs.edit().putString("language", lang).apply()

    // Apply locale using your helper
    LocaleHelper.setLocale(context, lang)

    // Refresh activity to reload strings
    activity.recreate()

    onDone()
}



@Composable
fun LanguageButton(
    flag: Int,
    isSelected: Boolean,
    onClick: () -> Unit   // <-- remove @Composable here
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
            .clickable { onClick() },  // now works

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
