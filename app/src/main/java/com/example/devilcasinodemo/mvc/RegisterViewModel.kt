package com.example.devilcasinodemo.mvc

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devilcasinodemo.mvc.dto.RegisterRequest
import com.example.devilcasinodemo.retrofit.ApiClient
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    fun register(
        username: String,
        email: String,
        password: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = ApiClient.api.register(
                    RegisterRequest(
                        email = email,
                        password = password,
                        nombre = username
                    )
                )

                if (response.isSuccessful && response.body() != null) {
                    onResult(true, response.body()!!.message)
                } else {
                    val error = response.errorBody()?.string()
                        ?: "Error al crear cuenta"
                    onResult(false, error)
                }

            } catch (e: Exception) {
                onResult(false, "Error de red: ${e.localizedMessage}")
            }
        }
    }
}
