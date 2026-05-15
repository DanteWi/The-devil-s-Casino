package com.example.devilcasinodemo.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = NeonOrange,
    secondary = NeonYellow,
    tertiary = NeonRed,
    background = DevilBlack,
    surface = DevilDarkGray,
    onPrimary = DevilTextWhite,
    onSecondary = DevilTextWhite,
    onBackground = DevilTextWhite,
    onSurface = DevilTextWhite
)

private val LightColorScheme = lightColorScheme(
    primary = NeonRed,
    secondary = NeonOrange,
    tertiary = NeonYellow,
    background = DevilTextWhite,
    surface = Color(0xFFFFF8E7),
    onPrimary = DevilBlack,
    onSecondary = DevilBlack,
    onBackground = DevilBlack,
    onSurface = DevilBlack
)


@Composable
fun DevilCasinoDemoTheme(
    darkTheme: Boolean = true, // Force dark as default for your casino
    dynamicColor: Boolean = false, // disable dynamic color for consistency
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = DevilTypography,
        content = content
    )
}
