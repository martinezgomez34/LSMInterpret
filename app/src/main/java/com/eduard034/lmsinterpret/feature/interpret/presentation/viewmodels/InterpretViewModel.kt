package com.eduard034.lmsinterpret.feature.interpret.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eduard034.lmsinterpret.core.data.local.UserSession
import com.eduard034.lmsinterpret.feature.interpret.domain.entities.Sena
import com.eduard034.lmsinterpret.feature.interpret.domain.usecases.InterpretarUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// Estado de la UI
data class InterpretUiState(
    val isLoading: Boolean = false,
    val items: List<Sena> = emptyList(),
    val error: String? = null,
    val userName: String? = null // <- Añadimos el nombre de usuario al estado
)

@HiltViewModel // <- Anotación de Hilt
class InterpretViewModel @Inject constructor( // <- Inyección de dependencias
    private val interpretarUseCase: InterpretarUseCase,
    private val userSession: UserSession // <- Inyectamos UserSession
) : ViewModel() {

    private val _uiState = MutableStateFlow(InterpretUiState())
    val uiState = _uiState.asStateFlow()

    init {
        // Cargar el nombre de usuario al iniciar
        viewModelScope.launch {
            userSession.userName.collect { name ->
                _uiState.update { it.copy(userName = name) }
            }
        }
    }

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

    // Función para cerrar sesión
    fun logout() {
        viewModelScope.launch {
            userSession.clear()
        }
    }
}