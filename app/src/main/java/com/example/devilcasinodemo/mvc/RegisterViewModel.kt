package com.example.devilcasinodemo.mvc

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devilcasinodemo.mvc.dto.RegisterRequest
import com.example.devilcasinodemo.retrofit.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class RegisterViewModel : ViewModel() {

    companion object {
        private const val TAG = "RegisterViewModel"
    }

    var isLoading by mutableStateOf(false)
        private set

    var registerState by mutableStateOf<String?>(null)
        private set

    fun register(
        username: String,
        email: String,
        password: String,
        onResult: (Boolean, String?) -> Unit
    ) {

        if (isLoading) return

        viewModelScope.launch {

            isLoading = true
            registerState = "LOADING"

            Log.d(TAG, "Attempting registration for $email")

            runCatching {

                withContext(Dispatchers.IO) {
                    ApiClient.api.register(
                        RegisterRequest(
                            email = email,
                            password = password,
                            nombre = username
                        )
                    )
                }

            }.onSuccess { response ->

                if (response.isSuccessful && response.body() != null) {

                    val msg = response.body()!!.message
                    registerState = "OK"

                    Log.i(TAG, "Registration successful: $email")

                    onResult(true, msg)

                } else {

                    val errorMsg = response.errorBody()?.string() ?: "Error al crear cuenta"
                    registerState = "ERROR"

                    Log.w(TAG, "Registration failed: $errorMsg")

                    onResult(false, errorMsg)
                }

            }.onFailure { throwable ->

                val message = when (throwable) {
                    is IOException -> "Sin conexión a internet"
                    is HttpException -> "Error del servidor (${throwable.code()})"
                    else -> "Error inesperado"
                }

                registerState = "NETWORK_ERROR"
                Log.e(TAG, "Registration error", throwable)
                onResult(false, message)
            }.also {
                isLoading = false
            }
        }
    }
}
