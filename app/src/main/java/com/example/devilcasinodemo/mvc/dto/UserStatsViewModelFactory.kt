package com.example.devilcasinodemo.mvc.dto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.devilcasinodemo.mvc.UserStatsViewModel
import com.example.devilcasinodemo.retrofit.ApiService

class UserStatsViewModelFactory(
    private val api: ApiService
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserStatsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserStatsViewModel(api) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}