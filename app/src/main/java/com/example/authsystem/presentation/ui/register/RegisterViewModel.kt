package com.example.authsystem.presentation.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authsystem.domain.data.User
import com.example.authsystem.domain.usecase.RegisterUseCase
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
    private val _state = MutableLiveData<RegisterState>()
    val state: LiveData<RegisterState> = _state

    fun registerUser(user: User) {
        viewModelScope.launch {
            try {
                val response = registerUseCase(user)
                if (response.isSuccess) {
                    _state.value = RegisterState.Success(user)
                } else {
                    _state.value = RegisterState.Error(
                        response.exceptionOrNull()?.message ?: "Não foi possível concluir o cadastro. Tente novamente mais tarde."
                    )
                }
            } catch (e: Exception) {
                _state.value = RegisterState.Error("Não foi possível concluir o cadastro. Tente novamente mais tarde.")
            }
        }
    }
}