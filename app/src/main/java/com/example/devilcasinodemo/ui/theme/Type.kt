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
    titleLarge = TextStyle(
        fontFamily = CasinoFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        letterSpacing = 1.sp,
        color = NeonYellow
    ),
    bodyLarge = TextStyle(
        fontFamily = CasinoFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = DevilTextWhite
    )
)
