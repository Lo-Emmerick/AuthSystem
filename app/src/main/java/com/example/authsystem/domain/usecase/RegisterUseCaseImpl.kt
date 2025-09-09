package com.example.authsystem.domain.usecase

import android.util.Patterns
import com.example.authsystem.domain.data.User
import com.example.authsystem.domain.repository.RegisterRepotitory

class RegisterUseCaseImpl(
private val repository: RegisterRepotitory
) : RegisterUseCase {

    override suspend operator fun invoke(user: User): Result<Unit> {
        if (user.name.isEmpty()) {
            return Result.failure(IllegalArgumentException("NameEmpty"))
        }

        if (user.email.isEmpty()) {
            return Result.failure(IllegalArgumentException("EmailEmpty"))
        }

        if (user.password.isEmpty()) {
            return Result.failure(IllegalArgumentException("PasswordEmpty"))
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(user.email).matches()) {
            return Result.failure(IllegalArgumentException("EmailInvalid"))
        }

        if (user.password.length < 6) {
            return Result.failure(IllegalArgumentException("PasswordInvalid"))
        }

        return repository.registerUser(user)
    }
}