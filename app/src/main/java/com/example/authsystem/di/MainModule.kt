package com.example.authsystem.di

import com.example.authsystem.data.repository.RegisterRepositoryImpl
import com.example.authsystem.domain.repository.RegisterRepotitory
import com.example.authsystem.domain.usecase.RegisterUseCase
import com.example.authsystem.domain.usecase.RegisterUseCaseImpl
import com.example.authsystem.presentation.ui.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loadRepositories = module {
    single { RegisterRepositoryImpl() as RegisterRepotitory }
}

val loadUseCase = module {
    single { RegisterUseCaseImpl(repository = get()) as RegisterUseCase }
}

val loadViewModel = module {
    viewModel {
        RegisterViewModel(
            registerUseCase = get()
        )
    }
}