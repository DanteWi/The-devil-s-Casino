package com.example.devilcasinodemo.mvc

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devilcasinodemo.retrofit.ApiClient
import com.example.devilcasinodemo.mvc.conexion.LoginRequest
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    var userId by mutableStateOf<Long?>(null)
        private set

    private var loginState: String? = null
        private set

    fun login(email: String, password: String, onResult: (success: Boolean, errorMessage: String?) -> Unit) {
        viewModelScope.launch {
            try {
                val response = ApiClient.api.login(LoginRequest(email, password))
                if (response.isSuccessful && response.body() != null) {
                    loginState = "OK"
                    // Save userId from backend response
                    userId = response.body()!!.userId
                    onResult(true, response.body()!!.message)
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

