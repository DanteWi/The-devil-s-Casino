package com.example.devilcasinodemo.mvc.conexion

data class BlackjackState(
    val deck: List<String>,
    val playerCards: List<String>,
    val dealerCards: List<String>,
    val playerTotal: Int,
    val dealerTotal: Int,
    val bet: Double,
    val status: String,
    val finished: Boolean
)
