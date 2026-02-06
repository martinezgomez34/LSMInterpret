package com.eduard034.lmsinterpret.feature.login.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eduard034.lmsinterpret.core.data.local.UserSession
import com.eduard034.lmsinterpret.feature.login.domain.usecases.LoginUseCase

class LoginViewModelFactory(
    private val loginUseCase: LoginUseCase,
    private val userSession: UserSession
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(loginUseCase, userSession) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}