package com.example.devilcasinodemo.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.devilcasinodemo.R

val CasinoFont = FontFamily(
    Font(R.font.font) // Add font file under res/font/
)

val DevilTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = CasinoFont,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        letterSpacing = 1.5.sp,
        color = NeonOrange
    ),
    displayMedium = TextStyle(fontFamily = CasinoFont, fontWeight = FontWeight.Bold, fontSize = 28.sp, color = NeonOrange),
    displaySmall = TextStyle(fontFamily = CasinoFont, fontWeight = FontWeight.Bold, fontSize = 24.sp, color = NeonOrange),

    headlineLarge = TextStyle(fontFamily = CasinoFont, fontWeight = FontWeight.SemiBold, fontSize = 24.sp, color = NeonYellow),
    headlineMedium = TextStyle(fontFamily = CasinoFont, fontWeight = FontWeight.SemiBold, fontSize = 20.sp, color = NeonYellow),
    headlineSmall = TextStyle(fontFamily = CasinoFont, fontWeight = FontWeight.SemiBold, fontSize = 18.sp, color = NeonYellow),

    titleLarge = TextStyle(fontFamily = CasinoFont, fontWeight = FontWeight.SemiBold, fontSize = 22.sp, color = NeonYellow),
    titleMedium = TextStyle(fontFamily = CasinoFont, fontWeight = FontWeight.Medium, fontSize = 16.sp, color = NeonYellow),
    titleSmall = TextStyle(fontFamily = CasinoFont, fontWeight = FontWeight.Medium, fontSize = 14.sp, color = NeonYellow),

    bodyLarge = TextStyle(fontFamily = CasinoFont, fontWeight = FontWeight.Normal, fontSize = 16.sp, color = DevilTextWhite),
    bodyMedium = TextStyle(fontFamily = CasinoFont, fontWeight = FontWeight.Normal, fontSize = 14.sp, color = DevilTextWhite),
    bodySmall = TextStyle(fontFamily = CasinoFont, fontWeight = FontWeight.Normal, fontSize = 12.sp, color = DevilTextWhite),

    labelLarge = TextStyle(fontFamily = CasinoFont, fontWeight = FontWeight.Medium, fontSize = 14.sp, color = DevilTextWhite),
    labelMedium = TextStyle(fontFamily = CasinoFont, fontWeight = FontWeight.Medium, fontSize = 12.sp, color = DevilTextWhite),
    labelSmall = TextStyle(fontFamily = CasinoFont, fontWeight = FontWeight.Medium, fontSize = 10.sp, color = DevilTextWhite)
)
