package com.example.devilcasinodemo.mvc

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

class BlackjackViewModel(
    private val api: ApiService = ApiClient.api
) : ViewModel() {

    var gameState by mutableStateOf(BlackjackState())

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

    fun startGame(userId: Long, bet: Double) {
        viewModelScope.launch {
            val response = api.startGame(StartGameRequest(userId, bet))

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
        }
    }

    fun hit(userId: Long) {
        viewModelScope.launch {
            val response = api.hit(userId)
            gameState = response.safe()
        }
    }

    fun stand(userId: Long) {
        viewModelScope.launch {
            val response = api.stand(userId)
            gameState = response.safe()
        }
    }

    fun resetGame() {
        gameState = BlackjackState()
    }

}

