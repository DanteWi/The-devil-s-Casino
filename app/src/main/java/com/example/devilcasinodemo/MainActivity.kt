package com.example.devilcasinodemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.devilcasinodemo.navigator.AppNavHost
import com.example.devilcasinodemo.ui.theme.DevilCasinoDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DevilCasinoDemoTheme {
                val navController = rememberNavController() // ✅ Proper NavController
                AppNavHost(navController = navController, startDestination = "login")
            }
        }
    }
}
