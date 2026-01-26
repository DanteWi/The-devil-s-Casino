package com.example.devilcasinodemo.mvc

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devilcasinodemo.retrofit.ApiService
import kotlinx.coroutines.launch

class UserStatsViewModel(
    private val api: ApiService
) : ViewModel() {

    var winRate by mutableStateOf(0.0)
    var lossRate by mutableStateOf(0.0)
        private set

    fun loadStats(userId: Long) {
        viewModelScope.launch {
            val res = api.getBlackjackStats(userId)
            winRate = res.winRate
            lossRate = res.lossRate
        }
    }
}
