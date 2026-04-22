package com.example.devilcasinodemo.mvc

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devilcasinodemo.mvc.dto.LiarDiceState
import com.example.devilcasinodemo.retrofit.ApiClient
import com.example.devilcasinodemo.retrofit.ApiService
import kotlinx.coroutines.launch

class DevilDiceViewModel(
    private val api: ApiService = ApiClient.api
) : ViewModel() {

    var gameState by mutableStateOf(LiarDiceState())

    fun createGame(userId: Long, bet: Double, vsAI: Boolean) {
        viewModelScope.launch {
            runCatching {
                api.createDiceGame(userId, bet, vsAI)
            }.onSuccess {
                gameState = it
            }
        }
    }

    fun joinGame(gameId: Long, userId: Long) {
        viewModelScope.launch {
            runCatching {
                api.joinDiceGame(gameId, userId)
            }.onSuccess {
                gameState = it
            }
        }
    }

    fun bet(gameId: Long, userId: Long, quantity: Int, faceValue: Int) {
        viewModelScope.launch {
            runCatching {
                api.diceBet(gameId, userId, quantity, faceValue)
            }
        }
    }

    fun call(gameId: Long, userId: Long) {
        viewModelScope.launch {
            runCatching {
                api.callLie(gameId, userId)
            }
        }
    }

    fun refreshState(userId: Long) {
        viewModelScope.launch {

            runCatching {
                api.getDiceState(userId)
            }.onSuccess {
                gameState = it
            }.onFailure {
                Log.e("DevilDiceViewModel", "refreshState error", it)
            }

        }
    }
}