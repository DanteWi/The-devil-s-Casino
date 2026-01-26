package com.example.devilcasinodemo.retrofit

import com.example.devilcasinodemo.mvc.dto.BlackjackState
import retrofit2.http.*

interface BlackjackApi {
    @POST("/blackjack/start")
    suspend fun startGame(
        @Query("userId") userId: Long,
        @Query("bet") bet: Double
    ): BlackjackState

    @POST("/blackjack/hit")
    suspend fun hit(@Query("userId") userId: Long): BlackjackState

    @POST("/blackjack/stand")
    suspend fun stand(@Query("userId") userId: Long): BlackjackState

    @GET("/blackjack/state")
    suspend fun getState(@Query("userId") userId: Long): BlackjackState
}
