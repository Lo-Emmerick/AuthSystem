package com.example.authsystem.presentation.ui.register

import com.example.authsystem.domain.data.User

sealed class RegisterState {
    object Loading : RegisterState()
    data class Success(val user: User) : RegisterState()
    data class Error(val field: String, val message: String) : RegisterState()
}