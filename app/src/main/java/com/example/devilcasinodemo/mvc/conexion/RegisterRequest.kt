package com.example.devilcasinodemo.mvc.conexion

data class RegisterRequest(
    val email: String,
    val password: String,
    val nombre: String
)