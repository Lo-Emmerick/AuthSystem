package com.example.authsystem.presentation.ui.register

import com.example.authsystem.domain.data.User

interface RegisterState {
    data class Success(val user: User) : RegisterState
    object Loading : RegisterState
    object Empty : RegisterState
    data class Error(val message: String) : RegisterState
}