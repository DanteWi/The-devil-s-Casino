package com.example.devilcasinodemo.mvc.dto

data class WinLossResponse(
    val wins: Int,
    val losses: Int,
    val winRate: Double,
    val lossRate: Double
)
