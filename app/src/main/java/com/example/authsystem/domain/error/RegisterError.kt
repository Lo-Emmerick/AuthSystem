package com.example.authsystem.domain.error

sealed class RegisterError(message: String) : Exception(message) {
    object NameEmpty : RegisterError("Campo nome vazio")
    object EmailEmpty : RegisterError("Campo email vazio")
    object PasswordEmpty : RegisterError("Campo senha vazio")
    object EmailInvalid : RegisterError("Email inválido")
    object PasswordInvalid : RegisterError("Senha deve ter no mínimo 6 caracteres")
    object EmailAlreadyExists : RegisterError("Este email já está cadastrado")
    object Unknown : RegisterError("Erro desconhecido")
}