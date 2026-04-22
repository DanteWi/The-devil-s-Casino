package com.example.devilcasinodemo.mvc.dto

data class LiarDiceState(
    val gameId: Long = 0,
    val status: String = "",
    val currentTurn: Long? = null,
    val currentBet: String? = null,
    val players: List<PlayerSummary> = emptyList(),
    val myDice: List<Int> = emptyList()
)