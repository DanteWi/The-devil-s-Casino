package com.example.devilcasinodemo.mvc

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devilcasinodemo.mvc.dto.BlackjackState
import com.example.devilcasinodemo.mvc.dto.StartGameRequest
import com.example.devilcasinodemo.retrofit.ApiClient
import com.example.devilcasinodemo.retrofit.ApiService
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class BlackjackViewModel(
    private val api: ApiService = ApiClient.api
) : ViewModel() {

    companion object {
        private const val TAG = "BlackjackViewModel"
    }

    var gameState by mutableStateOf(BlackjackState())

    var errorMessage by mutableStateOf<String?>(null)
        private set

    /* ---------------- Safe Mapping ---------------- */
    private fun BlackjackState.safe(): BlackjackState {
        return BlackjackState(
            deck = deck ?: emptyList(),
            playerCards = playerCards ?: emptyList(),
            dealerCards = dealerCards ?: emptyList(),
            playerTotal = playerTotal ?: 0,
            dealerTotal = dealerTotal ?: 0,
            bet = bet ?: 0.0,
            status = status ?: "",
            finished = finished ?: false
        )
    }

    /* ---------------- API Calls ---------------- */
    fun startGame(userId: Long, bet: Double) {


        viewModelScope.launch {
            Log.d(TAG, "startGame(userId=$userId, bet=$bet)")
            runCatching {
                api.startGame(StartGameRequest(userId, bet))
            }.onSuccess { response ->
                gameState = BlackjackState(
                    deck = response.deck ?: emptyList(),
                    playerCards = response.playerCards ?: emptyList(),
                    dealerCards = response.dealerCards ?: emptyList(),
                    playerTotal = response.playerTotal ?: 0,
                    dealerTotal = response.dealerTotal ?: 0,
                    bet = response.bet ?: bet,
                    status = response.status ?: "",
                    finished = response.finished ?: false
                )
                errorMessage = null
                Log.d(TAG, "startGame success")
            }.onFailure { throwable ->
                handleError("startGame", throwable)
            }
        }
    }

    fun hit(userId: Long) {
        viewModelScope.launch {
            runCatching { api.hit(userId) }
                .onSuccess { response ->
                    gameState = response.safe()
                    errorMessage = null
                    Log.d(TAG, "hit success")
                }
                .onFailure { throwable -> handleError("hit", throwable) }
        }
    }

    fun stand(userId: Long) {
        viewModelScope.launch {
            runCatching { api.stand(userId) }
                .onSuccess { response ->
                    gameState = response.safe()
                    errorMessage = null
                    Log.d(TAG, "stand success")
                }
                .onFailure { throwable -> handleError("stand", throwable) }
        }
    }

    fun resetGame() {
        Log.d(TAG, "resetGame()")
        gameState = BlackjackState()
        errorMessage = null
    }

    /* ---------------- Error Handler ---------------- */
    private fun handleError(source: String, throwable: Throwable) {
        val message = when (throwable) {
            is IOException -> "No internet connection"
            is HttpException -> "Server error (${throwable.code()})"
            else -> "Unexpected error"
        }
        Log.e(TAG, "Error in $source", throwable)
        errorMessage = message
    }
}
