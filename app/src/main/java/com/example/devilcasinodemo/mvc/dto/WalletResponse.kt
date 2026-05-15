package com.example.devilcasinodemo.mvc.dto

data class WalletResponse(
    val userId: Long,
    val saldoDC: Double,
    val lastFreeClaim: String?
)

