package com.example.devilcasinodemo.navigator

import Wallet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.devilcasinodemo.paginas.gamesPages.BlackjackScreen
import com.example.devilcasinodemo.paginas.gamesPages.DevilDicesScreen
import com.example.devilcasinodemo.paginas.login.CreateAccountScreen
import com.example.devilcasinodemo.paginas.lobby.Lobby
import com.example.devilcasinodemo.paginas.login.Login
import com.example.devilcasinodemo.paginas.lobby.User

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

