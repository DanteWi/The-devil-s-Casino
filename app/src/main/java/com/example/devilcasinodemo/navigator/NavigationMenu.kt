package com.example.devilcasinodemo.navigator

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.devilcasinodemo.ui.theme.DevilCasinoDemoTheme

@RequiresApi(Build.VERSION_CODES.O)
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

    Scaffold(
        bottomBar = {
            if (currentRoute in bottomBarRoutes) {
                NavigationBar(containerColor = Color(0xFFFF0000)) {
                    bottomBarItems.forEachIndexed { index, item ->
                        val isSelected = currentRoute == item.route
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = {
                                navController.navigate(item.route) {
                                    launchSingleTop = true
                                }
                            },
                            icon = {
                                Icon(
                                    item.icon,
                                    contentDescription = item.label,
                                    tint = if (isSelected) Color(0xFFFFA500) else Color.Black
                                )
                            },
                            label = {
                                Text(
                                    item.label,
                                    color = if (isSelected) Color(0xFFFFA500) else Color.Black
                                )
                            },
                            alwaysShowLabel = true,
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color(0xFFFFA500),
                                unselectedIconColor = Color.Black,
                                selectedTextColor = Color(0xFFFFA500),
                                unselectedTextColor = Color.Black,
                                indicatorColor = Color.Transparent
                        ))
                    }
                }
            }

        }
    ) { padding ->
        AppNavHost(
            navController = navController,
            startDestination = "login", // Make sure the start destination matches your expectation
            modifier = Modifier.padding(padding)
        )
    }
}




@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun Preview556() {
    DevilCasinoDemoTheme {
        // Create a dummy NavController for preview
        val navController = rememberNavController()
        NavigatorMenu(navController = navController, )
    }
}