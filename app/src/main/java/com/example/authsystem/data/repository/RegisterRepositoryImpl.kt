package com.example.authsystem.data.repository

import com.example.authsystem.domain.data.User
import com.example.authsystem.domain.error.RegisterError
import com.example.authsystem.domain.repository.RegisterRepotitory
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.tasks.await

class RegisterRepositoryImpl(
    private val auth: FirebaseAuth = Firebase.auth
) : RegisterRepotitory {
    override suspend fun registerUser(user: User): Result<Unit> {
        return try {
            val result = auth.createUserWithEmailAndPassword(user.email, user.password).await()

            result.user?.updateProfile(
                userProfileChangeRequest { displayName = user.name }
            )?.await()

            Result.success(Unit)
        } catch (e: Exception) {
            val mappedError = when (e) {
                is FirebaseAuthUserCollisionException -> RegisterError.EmailAlreadyExists
                else -> RegisterError.Unknown
            }
            Result.failure(mappedError)
        }
    }
}