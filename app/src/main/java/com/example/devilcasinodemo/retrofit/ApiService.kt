package com.example.devilcasinodemo.retrofit

import com.example.devilcasinodemo.mvc.conexion.BlackjackState
import com.example.devilcasinodemo.mvc.conexion.LoginRequest
import com.example.devilcasinodemo.mvc.conexion.LoginResponse
import com.example.devilcasinodemo.mvc.conexion.StartGameRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>


    //Blackjack
    @POST("blackjack/start")
    suspend fun startGame(@Body request: StartGameRequest): BlackjackState

    @POST("/blackjack/hit")
    suspend fun hit(@Query("userId") userId: Long): BlackjackState

    @POST("/blackjack/stand")
    suspend fun stand(@Query("userId") userId: Long): BlackjackState

    @GET("/blackjack/state")
    suspend fun getState(@Query("userId") userId: Long): BlackjackState
}

