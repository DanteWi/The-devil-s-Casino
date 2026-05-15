package com.example.devilcasinodemo.mvc

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devilcasinodemo.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class UserStatsViewModel(
    private val api: ApiService
) : ViewModel() {

    companion object {
        private const val TAG = "UserStatsViewModel"
    }

    var winRate by mutableStateOf(0.0)
        private set
    var lossRate by mutableStateOf(0.0)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set


    fun loadStats(userId: Long) {

        if (isLoading) return  // prevent duplicate loads

        viewModelScope.launch {

            isLoading = true
            errorMessage = null

            Log.d(TAG, "Loading stats for userId=$userId")

            runCatching {

                withContext(Dispatchers.IO) {
                    api.getBlackjackStats(userId)
                }

            }.onSuccess { res ->

                winRate = res.winRate
                lossRate = res.lossRate

                Log.i(TAG, "Stats loaded successfully: winRate=${res.winRate}, lossRate=${res.lossRate}")

            }.onFailure { throwable ->

                val msg = when (throwable) {
                    is IOException -> "Sin conexión a internet"
                    is HttpException -> "Error del servidor (${throwable.code()})"
                    else -> "Error inesperado al cargar estadísticas"
                }

                Log.e(TAG, "Failed to load stats", throwable)
                errorMessage = msg
            }.also {
                isLoading = false
            }
        }
    }
}
