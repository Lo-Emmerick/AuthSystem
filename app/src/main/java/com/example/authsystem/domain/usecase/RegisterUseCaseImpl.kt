package com.example.authsystem.domain.usecase

import android.util.Patterns
import com.example.authsystem.domain.data.User
import com.example.authsystem.domain.repository.RegisterRepotitory

class RegisterUseCaseImpl(
private val repository: RegisterRepotitory
) : RegisterUseCase {

    override suspend operator fun invoke(user: User): Result<Unit> {
        if (user.name.isEmpty() || user.email.isEmpty() || user.password.isEmpty()) {
            return Result.failure(IllegalArgumentException("Os campos não podem estar vazios"))
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(user.email).matches()) {
            return Result.failure(IllegalArgumentException("Email inválido"))
        }

        if (user.password.length < 6) {
            return Result.failure(IllegalArgumentException("A senha deve ter pelo menos 6 caracteres"))
        }

        return repository.registerUser(user)
    }
}