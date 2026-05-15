package com.example.devilcasinodemo.mvc.dto

data class BlackjackState(
    val deck: List<String> = emptyList(),
    val playerCards: List<String> = emptyList(),
    val dealerCards: List<String> = emptyList(),
    val playerTotal: Int = 0,
    val dealerTotal: Int = 0,
    val bet: Double = 0.0,
    val status: String = "",
    val finished: Boolean = false
)
