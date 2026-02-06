package com.eduard034.lmsinterpret.feature.login.di

import com.eduard034.lmsinterpret.core.di.AppContainer
import com.eduard034.lmsinterpret.feature.login.domain.usecases.LoginUseCase
import com.eduard034.lmsinterpret.feature.login.domain.usecases.RegisterUseCase
import com.eduard034.lmsinterpret.feature.login.presentation.viewmodels.LoginViewModelFactory
import com.eduard034.lmsinterpret.feature.login.presentation.viewmodels.RegisterViewModelFactory

class LoginModule(private val appContainer: AppContainer) {

    private fun provideLoginUseCase(): LoginUseCase {
        return LoginUseCase(appContainer.authRepository)
    }

    private fun provideRegisterUseCase(): RegisterUseCase {
        return RegisterUseCase(appContainer.authRepository)
    }

    fun provideLoginViewModelFactory(): LoginViewModelFactory {
        return LoginViewModelFactory(
            loginUseCase = provideLoginUseCase(),
            userSession = appContainer.userSession
        )
    }

    fun provideRegisterViewModelFactory(): RegisterViewModelFactory {
        return RegisterViewModelFactory(
            registerUseCase = provideRegisterUseCase()
        )
    }
}