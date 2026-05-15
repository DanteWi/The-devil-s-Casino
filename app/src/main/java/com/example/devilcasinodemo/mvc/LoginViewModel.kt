package com.example.devilcasinodemo.mvc

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.devilcasinodemo.mvc.dto.LoginRequest
import com.example.devilcasinodemo.retrofit.ApiClient
import com.example.devilcasinodemo.util.TokenManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import android.app.Application

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        private const val TAG = "LoginViewModel"
    }

    val tokenManager = TokenManager(application)

    var userId by mutableStateOf<Long?>(null)
        private set

    var username by mutableStateOf<String?>(null)
        private set

    var loginState by mutableStateOf<String?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    init {
        userId = tokenManager.getUserId()
        username = tokenManager.getUsername()
    }

    fun logout() {
        tokenManager.clear()
        userId = null
        username = null
        loginState = null
    }
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
                    tokenManager.saveToken(body.token)
                    tokenManager.saveUser(body.userId, body.name)

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
            is IOException -> "There is a connection problem or the server is not responding"
            is HttpException -> "Server error (${throwable.code()})"
            else -> "An unexpected error occurred"
        }

        loginState = "NETWORK_ERROR"

        Log.e(TAG, "Login error", throwable)

        onResult(false, message)
    }
}
