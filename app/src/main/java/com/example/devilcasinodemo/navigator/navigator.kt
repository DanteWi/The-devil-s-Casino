package com.example.devilcasinodemo.navigator

import Wallet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.devilcasinodemo.mvc.BlackjackViewModel
import com.example.devilcasinodemo.mvc.LoginViewModel
import com.example.devilcasinodemo.paginas.gamesPages.BlackjackScreen
import com.example.devilcasinodemo.paginas.gamesPages.DevilDicesScreen
import com.example.devilcasinodemo.paginas.login.CreateAccountScreen
import com.example.devilcasinodemo.paginas.lobby.Lobby
import com.example.devilcasinodemo.paginas.login.Login
import com.example.devilcasinodemo.paginas.lobby.User

@Composable
fun AppNavHost(navController: NavHostController, startDestination: String, modifier: Modifier) {

    // Shared ViewModels across screens
    val loginViewModel: LoginViewModel = viewModel()
    val blackjackViewModel: BlackjackViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        composable("login") {
            Login(
                navController = navController,
                viewModel = loginViewModel
            )
        }

        composable("lobby") {
            Lobby(navController, loginViewModel)
        }

        composable("blackjack") {

            val userId = loginViewModel.userId

            if (userId != null) {
                BlackjackScreen(
                    navHostController = navController,
                    viewModel = blackjackViewModel,
                    userId = userId
                )
            } else {
                // User ID missing -> force login
                navController.navigate("login") {
                    popUpTo("blackjack") { inclusive = true }
                }
            }
        }

        composable("liars_dice") { DevilDicesScreen(navController) }
        composable("wallet") { Wallet(navController) }
        composable("create_user") { CreateAccountScreen(navController) }
        composable("user") { User(navController) }
    }
}



