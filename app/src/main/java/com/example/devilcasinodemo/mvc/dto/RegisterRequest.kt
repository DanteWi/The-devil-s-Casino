package com.example.devilcasinodemo.mvc.dto

data class RegisterRequest(
    val email: String,
    val password: String,
    val nombre: String
)