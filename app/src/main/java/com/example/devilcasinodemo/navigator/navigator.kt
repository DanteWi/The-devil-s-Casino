package com.example.devilcasinodemo.navigator

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.devilcasinodemo.gamesPages.BlackjackScreen
import com.example.devilcasinodemo.gamesPages.DevilDicesScreen
import com.example.devilcasinodemo.paginas.CreateAccountScreen
import com.example.devilcasinodemo.paginas.Lobby
import com.example.devilcasinodemo.paginas.Login
import com.example.devilcasinodemo.paginas.User
import com.example.devilcasinodemo.paginas.Wallet

@Composable
fun AppNavHost(navController: NavHostController, startDestination: String, modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable("lobby") { Lobby(navController) }
        composable("user") { User(navController) }
        composable("wallet") { Wallet(navController) }
        composable("blackjack") { BlackjackScreen(navController) }
        composable("liars_dice") { DevilDicesScreen(navController) }
        composable("login") { Login(navController) }
        composable("create_user") { CreateAccountScreen(navController) }
    }
}

