package com.example.devilcasinodemo.retrofit

import com.example.devilcasinodemo.mvc.dto.BlackjackState
import com.example.devilcasinodemo.mvc.dto.LoginRequest
import com.example.devilcasinodemo.mvc.dto.LoginResponse
import com.example.devilcasinodemo.mvc.dto.RegisterRequest
import com.example.devilcasinodemo.mvc.dto.RegisterResponse
import com.example.devilcasinodemo.mvc.dto.StartGameRequest
import com.example.devilcasinodemo.mvc.dto.WalletResponse
import com.example.devilcasinodemo.mvc.dto.WinLossResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("api/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>


    //Blackjack
    @POST("blackjack/start")
    suspend fun startGame(@Body request: StartGameRequest): BlackjackState

    @POST("/blackjack/hit")
    suspend fun hit(@Query("userId") userId: Long): BlackjackState

    @POST("/blackjack/stand")
    suspend fun stand(@Query("userId") userId: Long): BlackjackState

    @GET("/blackjack/state")
    suspend fun getState(@Query("userId") userId: Long): BlackjackState

    // Wallet endpoints
    @GET("api/wallet/{userId}")
    suspend fun getWallet(@Path("userId") userId: Long): Response<WalletResponse>

    @POST("api/wallet/{userId}/free")
    suspend fun claimFree(@Path("userId") userId: Long): Response<WalletResponse>

    @GET("api/wallet/{userId}/cooldown")
    suspend fun getFreeCooldown(@Path("userId") userId: Long): Long

    @POST("api/wallet/{userId}/purchase")
    suspend fun purchaseCoins(
        @Path("userId") userId: Long,
        @Query("amount") amount: Double
    ): Response<WalletResponse>

    // Stats

    @GET("api/stats/blackjack/{userId}")
    suspend fun getBlackjackStats(
        @Path("userId") userId: Long
    ): WinLossResponse

    @Multipart
    @POST("api/users/avatar")
    suspend fun uploadAvatar(
        @Part file: MultipartBody.Part,
        @Part("userId") userId: RequestBody
    ): Response<String>

}

