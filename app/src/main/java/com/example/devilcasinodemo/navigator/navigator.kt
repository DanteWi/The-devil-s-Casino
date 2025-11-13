package com.example.devilcasinodemo.navigator

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.devilcasinodemo.paginas.CreateAccountScreen
import com.example.devilcasinodemo.paginas.Lobby
import com.example.devilcasinodemo.paginas.Login
import com.example.devilcasinodemo.paginas.Wallet
import com.example.devilcasinodemo.paginas.User

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable("login") { Login(navController) } // Add your login screen here
        composable("crearuser") { CreateAccountScreen(navController) }
        composable("user") { User(navController) }
        composable("wallet") { Wallet(navController) }
        composable("lobby") { Lobby(navController) }
    }
}

