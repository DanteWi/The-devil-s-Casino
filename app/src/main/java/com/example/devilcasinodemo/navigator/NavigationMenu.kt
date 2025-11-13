package com.example.devilcasinodemo.navigator

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.devilcasinodemo.ui.theme.DevilCasinoDemoTheme

@Composable
fun NavigatorMenu(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    var selectedDestination by rememberSaveable { mutableIntStateOf(0) }

    val destinations = listOf(
        BottomNavDestination("User", "user", Icons.Default.Person),
        BottomNavDestination("Lobby", "lobby", Icons.Default.Home),
        BottomNavDestination("Wallet", "wallet", Icons.Default.AccountBalanceWallet),

        )

    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar(windowInsets = NavigationBarDefaults.windowInsets,containerColor = Color(0xFFDC143C)) {
                destinations.forEachIndexed { index, destination ->
                    NavigationBarItem(

                        selected = selectedDestination == index,
                        onClick = {
                            navController.navigate(destination.route)
                            selectedDestination = index
                        },
                        icon = {
                            Icon(
                                destination.icon,
                                contentDescription = destination.label
                            )
                        },
                        label = { Text(destination.label) },
                        alwaysShowLabel = true,
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFFFF9800),       // Black icon when selected
                            selectedTextColor = Color(0xFFFF9800),       // Black text when selected
                            unselectedIconColor = Color.Black.copy(alpha = 0.6f),  // Slightly faded black when unselected
                            unselectedTextColor = Color.Black.copy(alpha = 0.6f)
                        )
                    )
                }
            }
        }
    ) { contentPadding ->
        AppNavHost(
            navController = navController,
            startDestination = destinations[selectedDestination].route,
            modifier = Modifier.padding(contentPadding)
        )
    }
}




@Preview(showBackground = true)
@Composable
fun Preview556() {
    DevilCasinoDemoTheme {
        NavigatorMenu()
    }
}