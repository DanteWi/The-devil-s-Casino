package com.example.devilcasinodemo.util

import com.example.devilcasinodemo.mvc.dto.*
import com.example.devilcasinodemo.retrofit.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

 class FakeApiService : ApiService {

    // LOGIN
    var loginResponse: Response<LoginResponse>? = null

    // REGISTER
    var registerResponse: Response<RegisterResponse>? = null

    // WALLET
    var walletResponse: Response<WalletResponse>? = null
    var cooldown: Long = 0
    var purchaseResponse: Response<WalletResponse>? = null

    // STATS
    var statsResponse: WinLossResponse? = null

    // BLACKJACK
    var startResponse: BlackjackState? = null
    var hitResponse: BlackjackState? = null
    var standResponse: BlackjackState? = null


    override suspend fun login(request: LoginRequest)
            = loginResponse!!

    override suspend fun register(request: RegisterRequest)
            = registerResponse!!

    override suspend fun getWallet(userId: Long)
            = walletResponse!!

    override suspend fun claimFree(userId: Long): Response<WalletResponse> {
       val newBalance = (walletResponse?.body()?.saldoDC ?: 0.0) + 100.0
       val newWallet = WalletResponse(
          userId = userId,
          saldoDC = newBalance,
          lastFreeClaim = System.currentTimeMillis().toString()
       )
       walletResponse = Response.success(newWallet) // update walletResponse for fetchWallet
       return Response.success(newWallet)
    }



     override suspend fun getFreeCooldown(userId: Long)
            = cooldown

    override suspend fun purchaseCoins(userId: Long, amount: Double)
            = purchaseResponse!!

    override suspend fun getBlackjackStats(userId: Long)
            = statsResponse!!

    override suspend fun uploadAvatar(
       file: MultipartBody.Part,
       userId: RequestBody
    ): Response<String> {
       TODO("Not yet implemented")
    }

    override suspend fun startGame(request: StartGameRequest)
            = startResponse!!

    override suspend fun hit(userId: Long)
            = hitResponse!!

    override suspend fun stand(userId: Long)
            = standResponse!!

    override suspend fun getState(userId: Long)
            = BlackjackState()
}