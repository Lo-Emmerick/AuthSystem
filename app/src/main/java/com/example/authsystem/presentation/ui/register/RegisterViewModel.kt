package com.example.authsystem.presentation.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authsystem.domain.data.User
import com.example.authsystem.domain.error.RegisterError
import com.example.authsystem.domain.usecase.RegisterUseCase
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _state = MutableLiveData<RegisterState>()
    val state: LiveData<RegisterState> = _state

    fun registerUser(user: User) {
        viewModelScope.launch {
            _state.value = RegisterState.Loading

            val result = registerUseCase(user)

            if (result.isSuccess) {
                _state.value = RegisterState.Success(user)
            } else {
                val error = result.exceptionOrNull()
                val (field, message) = when (error) {
                    is RegisterError.NameEmpty -> "NameEmpty" to error.message!!
                    is RegisterError.EmailEmpty -> "EmailEmpty" to error.message!!
                    is RegisterError.EmailInvalid -> "EmailInvalid" to error.message!!
                    is RegisterError.EmailAlreadyExists -> "EmailAlreadyExists" to error.message!!
                    is RegisterError.PasswordEmpty -> "PasswordEmpty" to error.message!!
                    is RegisterError.PasswordInvalid -> "PasswordInvalid" to error.message!!
                    else -> "global" to "Erro desconhecido"
                }

                _state.value = RegisterState.Error(field, message)
            }
        }
    }
}