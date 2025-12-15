package com.example.devilcasinodemo.mvc

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devilcasinodemo.mvc.conexion.BlackjackState
import com.example.devilcasinodemo.mvc.conexion.StartGameRequest
import com.example.devilcasinodemo.retrofit.ApiClient
import com.example.devilcasinodemo.retrofit.ApiService
import com.example.devilcasinodemo.retrofit.BlackjackApi
import kotlinx.coroutines.launch

class BlackjackViewModel(
    private val api: ApiService = ApiClient.api
) : ViewModel() {

    var gameState by mutableStateOf<BlackjackState?>(null)

    fun startGame(userId: Long, bet: Double) {
        viewModelScope.launch {
            gameState = api.startGame(StartGameRequest(userId, bet))
        }
    }

    fun hit(userId: Long) {
        viewModelScope.launch {
            gameState = api.hit(userId)
        }
    }

    fun stand(userId: Long) {
        viewModelScope.launch {
            gameState = api.stand(userId)
        }
    }
}

