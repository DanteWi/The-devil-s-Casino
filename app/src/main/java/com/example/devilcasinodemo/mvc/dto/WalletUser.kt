package com.example.devilcasinodemo.mvc.dto


data class Wallet(
    val id: Long,
    val user: User,
    val saldoDC: Double,
    val lastFreeClaim: String? = null
)

data class User(
    val id: Long,
    val username: String
)
