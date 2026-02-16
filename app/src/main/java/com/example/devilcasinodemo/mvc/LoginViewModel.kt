package com.example.devilcasinodemo.mvc

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devilcasinodemo.mvc.dto.LoginRequest
import com.example.devilcasinodemo.retrofit.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class LoginViewModel : ViewModel() {

    companion object {
        private const val TAG = "LoginViewModel"
    }

    var userId by mutableStateOf<Long?>(null)
        private set

    var username by mutableStateOf<String?>(null)
        private set

    var loginState by mutableStateOf<String?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set


    fun login(
        email: String,
        password: String,
        onResult: (Boolean, String) -> Unit
    ) {

        if (isLoading) return  // prevent spamming

        viewModelScope.launch {

            isLoading = true
            loginState = "LOADING"

            Log.d(TAG, "Attempting login for $email")

            runCatching {

                withContext(Dispatchers.IO) {
                    ApiClient.api.login(LoginRequest(email, password))
                }

            }.onSuccess { response ->

                Log.d(TAG, "HTTP status: ${response.code()}")

                if (response.isSuccessful && response.body() != null) {

                    val body = response.body()!!

                    userId = body.userId
                    username = body.name
                    loginState = "OK"

                    Log.i(TAG, "Login successful: userId=${body.userId}")

                    onResult(true, body.message ?: "Login correcto")

                } else {

                    loginState = "ERROR"

                    val errorMsg = response.errorBody()?.string() ?: "Credenciales incorrectas"

                    Log.w(TAG, "Login failed: $errorMsg")

                    onResult(false, errorMsg)
                }

            }.onFailure { throwable ->

                handleError(throwable, onResult)

            }.also {

                isLoading = false
            }
        }
    }


    private fun handleError(
        throwable: Throwable,
        onResult: (Boolean, String) -> Unit
    ) {

        val message = when (throwable) {
            is IOException -> "Sin conexión a internet"
            is HttpException -> "Error del servidor (${throwable.code()})"
            else -> "Error inesperado"
        }

        loginState = "NETWORK_ERROR"

        Log.e(TAG, "Login error", throwable)

        onResult(false, message)
    }
}
