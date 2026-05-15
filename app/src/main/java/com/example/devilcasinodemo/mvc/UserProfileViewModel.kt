package com.example.devilcasinodemo.mvc

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devilcasinodemo.retrofit.ApiClient
import com.example.devilcasinodemo.utils.uriToFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.IOException

class UserProfileViewModel : ViewModel() {

    companion object {
        private const val TAG = "UserProfileViewModel"
    }

    // UI State
    private val _avatarVersion = MutableStateFlow(0)
    val avatarVersion: StateFlow<Int> = _avatarVersion

    private val _uploading = MutableStateFlow(false)
    val uploading: StateFlow<Boolean> = _uploading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage


    fun uploadAvatar(
        context: Context,
        uri: Uri,
        userId: Long
    ) {

        if (_uploading.value) return  // prevent duplicate uploads

        viewModelScope.launch {

            _uploading.value = true
            _errorMessage.value = null

            Log.d(TAG, "Uploading avatar for userId=$userId")

            runCatching {

                withContext(Dispatchers.IO) {
                    val file = uriToFile(context, uri)

                    val requestFile = file.asRequestBody("image/*".toMediaType())
                    val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
                    val userIdBody = userId.toString().toRequestBody("text/plain".toMediaType())

                    ApiClient.api.uploadAvatar(body, userIdBody)
                }

            }.onSuccess {
                Log.i(TAG, "Avatar upload successful")
                _avatarVersion.value++  // force reload in UI

            }.onFailure { throwable ->

                val message = when (throwable) {
                    is IOException -> "Sin conexión a internet"
                    is HttpException -> "Error del servidor (${throwable.code()})"
                    else -> "Error inesperado al subir avatar"
                }

                Log.e(TAG, "Avatar upload failed", throwable)
                _errorMessage.value = message
            }.also {
                _uploading.value = false
            }
        }
    }

    private val _cards = MutableStateFlow<List<com.example.devilcasinodemo.mvc.dto.CreditCardResponse>>(emptyList())
    val cards: StateFlow<List<com.example.devilcasinodemo.mvc.dto.CreditCardResponse>> = _cards

    fun fetchCards(userId: Long) {
        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) {
                    ApiClient.api.getCards(userId)
                }
            }.onSuccess { response ->
                if (response.isSuccessful) {
                    _cards.value = response.body() ?: emptyList()
                }
            }.onFailure { throwable ->
                Log.e(TAG, "Failed to fetch cards", throwable)
            }
        }
    }

    fun addCard(userId: Long, cardNumber: String, cardType: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) {
                    val request = com.example.devilcasinodemo.mvc.dto.CreditCardRequest(cardNumber, cardType)
                    ApiClient.api.addCard(userId, request)
                }
            }.onSuccess { response ->
                if (response.isSuccessful) {
                    val newCard = response.body()
                    if (newCard != null) {
                        _cards.value = _cards.value + newCard
                    }
                    onResult(true)
                } else {
                    onResult(false)
                }
            }.onFailure { throwable ->
                Log.e(TAG, "Failed to add card", throwable)
                onResult(false)
            }
        }
    }
}
