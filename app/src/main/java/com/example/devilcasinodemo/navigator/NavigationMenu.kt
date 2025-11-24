package com.example.devilcasinodemo.navigator

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.devilcasinodemo.ui.theme.DevilCasinoDemoTheme

@Composable
fun NavigatorMenu(navController: NavHostController) {

    val bottomBarRoutes = listOf("lobby", "user", "wallet")
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomBarItems = listOf(
        BottomNavDestination("User", "user", Icons.Default.Person),
        BottomNavDestination("Lobby", "lobby", Icons.Default.Home),
        BottomNavDestination("Wallet", "wallet", Icons.Default.AccountBalanceWallet)
    )

    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            if (currentRoute in bottomBarRoutes) {
                NavigationBar(containerColor = Color(0xFFDC143C)) {
                    bottomBarItems.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = selectedIndex == index,
                            onClick = {
                                navController.navigate(item.route) {
                                    launchSingleTop = true
                                }
                                selectedIndex = index
                            },
                            icon = { Icon(item.icon, item.label) },
                            label = { Text(item.label) }
                        )
                    }
                }
            }
        }
    ) { padding ->
        AppNavHost(
            navController = navController,
            startDestination = "login",
            modifier = Modifier.padding(padding)
        )
    }
}



@Preview(showBackground = true)
@Composable
fun Preview556() {
    DevilCasinoDemoTheme {
        // Create a dummy NavController for preview
        val navController = rememberNavController()
        NavigatorMenu(navController = navController, )
    }
}