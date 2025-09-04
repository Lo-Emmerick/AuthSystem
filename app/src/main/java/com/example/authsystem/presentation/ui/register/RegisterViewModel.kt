package com.example.authsystem.presentation.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RegisterViewModel(

) : ViewModel() {
    private val _state = MutableLiveData<RegisterState>()
    val state: LiveData<RegisterState> = _state
    
    fun registerUser() {
        viewModelScope.launch {
            try {

            } catch (e: Exception) {

            }
        }
    }
}