package com.example.devilcasinodemo.retrofit

import com.example.devilcasinodemo.mvc.conexion.LoginRequest
import com.example.devilcasinodemo.mvc.conexion.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}

