package com.example.devilcasinodemo.navigator

import Wallet
import WalletViewModel
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.devilcasinodemo.paginas.lobby.SettingsScreen
import com.example.devilcasinodemo.paginas.login.Login
import com.example.devilcasinodemo.paginas.lobby.User

@RequiresApi(Build.VERSION_CODES.O)
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

        composable("settings") {
            SettingsScreen()
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

        composable("wallet") {
            val walletViewModel: WalletViewModel = viewModel()
            val userId = loginViewModel.userId

            if (userId != null) {
                Wallet(
                    navController = navController,
                    viewModel = walletViewModel,
                    userId = userId
                )
            } else {
                // User not logged in → go back to login
                navController.navigate("login") {
                    popUpTo("wallet") { inclusive = true }
                }
            }
        }

        composable("liars_dice") { DevilDicesScreen(navController) }

        composable("create_user") { CreateAccountScreen(navController) }
        composable("user") {
            val userId = loginViewModel.userId

            if (userId != null) {
                User(
                    navController = navController,
                    userId = userId
                )
            } else {
                LaunchedEffect(Unit) {
                    navController.navigate("login") {
                        popUpTo("user") { inclusive = true }
                    }
                }
            }
        }
    }
}
