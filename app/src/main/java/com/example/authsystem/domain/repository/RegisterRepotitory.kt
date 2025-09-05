package com.example.authsystem.domain.repository

import com.example.authsystem.domain.data.User

interface RegisterRepotitory {
    suspend fun registerUser(user: User) : Result<Unit>
}