package com.example.authsystem.domain.usecase

import com.example.authsystem.domain.data.User

interface RegisterUseCase {
    suspend operator fun invoke(user: User): Boolean
}