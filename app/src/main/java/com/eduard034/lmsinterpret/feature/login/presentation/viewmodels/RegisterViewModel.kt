package com.eduard034.lmsinterpret.feature.login.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eduard034.lmsinterpret.feature.login.domain.usecases.RegisterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class RegisterUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    fun register(username: String, correo: String, pass: String) {
        _uiState.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            val result = registerUseCase(username, correo, pass)

            _uiState.update { currentState ->
                result.fold(
                    onSuccess = { currentState.copy(isLoading = false, isSuccess = true) },
                    onFailure = { error -> currentState.copy(isLoading = false, error = error.message) }
                )
            }
        }
    }

    fun resetState() {
        _uiState.update { RegisterUiState() }
    }
}