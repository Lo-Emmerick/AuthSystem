package com.example.authsystem.presentation.ui.register

interface RegisterState {
    object Success : RegisterState
    object Loading : RegisterState
    object Empty : RegisterState
    object Error : RegisterState}