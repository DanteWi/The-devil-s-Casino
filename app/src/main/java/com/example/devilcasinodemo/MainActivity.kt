package com.example.devilcasinodemo

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.devilcasinodemo.lenguas.LocaleHelper
import com.example.devilcasinodemo.music.MusicManager
import com.example.devilcasinodemo.navigator.NavigatorMenu
import com.example.devilcasinodemo.ui.theme.DevilCasinoDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DevilCasinoDemoTheme {
                val navController = rememberNavController()
                NavigatorMenu(
                    navController = navController,
                )
            }
        }
    }
    override fun onStart() {
        super.onStart()
        // Start music when the app comes to foreground
        MusicManager.start(this, R.raw.casino_music)
    }

    override fun onStop() {
        super.onStop()
        // Optional: pause music when app goes to background
        MusicManager.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Optional: release resources
        MusicManager.stop()
    }

    override fun attachBaseContext(newBase: Context) {

        val prefs = newBase.getSharedPreferences("settings", Context.MODE_PRIVATE)
        val lang = prefs.getString("language", "en") ?: "en"

        val context = LocaleHelper.setLocale(newBase, lang)

        super.attachBaseContext(context)
    }


}

