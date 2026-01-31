package com.eduard034.lmsinterpret.feature.interpret.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.eduard034.lmsinterpret.feature.interpret.domain.entities.Sena
import com.eduard034.lmsinterpret.feature.interpret.domain.usecases.InterpretarUseCase // O crear un GetSenaByNameUseCase
import com.eduard034.lmsinterpret.feature.interpret.domain.repositories.InterpretRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SenaDetailUiState(
    val isLoading: Boolean = false,
    val sena: Sena? = null,
    val error: String? = null
)

class SenaDetailViewModel(
    private val repository: InterpretRepository // Reusamos el repositorio
) : ViewModel() {

    private val _uiState = MutableStateFlow(SenaDetailUiState())
    val uiState = _uiState.asStateFlow()

    // Cargar la seña por nombre cuando se inicia la vista
    fun loadSena(nombre: String) {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            try {
                // Aquí deberíamos tener un método específico en el repo,
                // pero por ahora simularemos que interpretamos la palabra exacta
                // OJO: Lo ideal es agregar "getSenaByName" en tu repositorio.
                val result = repository.interpretarFrase(nombre)

                if (result.isNotEmpty()) {
                    _uiState.update { it.copy(isLoading = false, sena = result.first()) }
                } else {
                    _uiState.update { it.copy(isLoading = false, error = "No encontrado") }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }
}

// Factory para inyección manual
class SenaDetailViewModelFactory(
    private val repository: InterpretRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SenaDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SenaDetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}