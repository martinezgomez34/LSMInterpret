package com.eduard034.lmsinterpret.feature.interpret.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eduard034.lmsinterpret.feature.interpret.domain.entities.Sena
import com.eduard034.lmsinterpret.feature.interpret.domain.usecases.InterpretarUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Estado de la UI
data class InterpretUiState(
    val isLoading: Boolean = false,
    val items: List<Sena> = emptyList(),
    val error: String? = null
)

class InterpretViewModel(
    private val interpretarUseCase: InterpretarUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(InterpretUiState())
    val uiState = _uiState.asStateFlow()

    fun onTextChanged(texto: String) {

        _uiState.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            val result = interpretarUseCase(texto)

            _uiState.update { currentState ->
                result.fold(
                    onSuccess = { list ->
                        currentState.copy(isLoading = false, items = list)
                    },
                    onFailure = { error ->
                        currentState.copy(isLoading = false, error = error.message)
                    }
                )
            }
        }
    }
}