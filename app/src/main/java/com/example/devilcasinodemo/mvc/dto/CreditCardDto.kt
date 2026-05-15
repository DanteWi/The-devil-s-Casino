package com.example.devilcasinodemo.mvc.dto

data class CreditCardRequest(
    val cardNumber: String,
    val cardType: String
)

data class CreditCardResponse(
    val id: Long,
    val userId: Long,
    val cardNumber: String,
    val cardType: String
)
