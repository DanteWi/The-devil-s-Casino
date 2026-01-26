package com.example.devilcasinodemo.mvc

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devilcasinodemo.retrofit.ApiClient
import com.example.devilcasinodemo.mvc.dto.LoginRequest
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    var userId by mutableStateOf<Long?>(null)
        private set

    private var loginState: String? = null
        private set

    var username by mutableStateOf<String?>(null)
        private set

    fun login(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            try {
                val response = ApiClient.api.login(LoginRequest(email, password))

                if (response.isSuccessful && response.body() != null) {
                    val body = response.body()!!

                    loginState = "OK"
                    userId = body.userId
                    username = body.name   // or body.username — depending on backend

                    onResult(true, body.message)

                } else {
                    loginState = "ERROR"
                    val message = response.errorBody()?.string() ?: "Credenciales incorrectas"
                    onResult(false, message)
                }

            } catch (e: Exception) {
                loginState = "NETWORK_ERROR"
                onResult(false, "No se pudo conectar al servidor: ${e.localizedMessage}")
            }
        }
    }

}

