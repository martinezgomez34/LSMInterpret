package com.eduard034.lmsinterpret.feature.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.eduard034.lmsinterpret.core.data.local.UserSession
import com.eduard034.lmsinterpret.feature.profile.data.remote.UserProfileDto
import com.eduard034.lmsinterpret.feature.profile.domain.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ProfileUiState(
    val isLoading: Boolean = false,
    val userProfile: UserProfileDto? = null,
    val error: String? = null,
    val successMessage: String? = null,
    val isAccountDeleted: Boolean = false
)

class ProfileViewModel(
    private val repository: ProfileRepository,
    private val userSession: UserSession
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    // Cargar datos al iniciar
    init {
        loadUserProfile()
    }

    private fun loadUserProfile() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val token = userSession.userToken.first() ?: ""
            val storedName = userSession.userName.first() ?: ""

            val result = repository.getUserProfile(token, storedName)

            result.onSuccess { profile ->
                _uiState.update { it.copy(isLoading = false, userProfile = profile) }
            }.onFailure { e ->
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun updateUsername(newName: String) {
        val currentUser = _uiState.value.userProfile ?: return
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val token = userSession.userToken.first() ?: ""
            val result = repository.updateUsername(token, currentUser.id, newName)

            result.onSuccess {
                // Actualizamos la sesión local también
                userSession.saveSession(newName, token)
                _uiState.update { it.copy(isLoading = false, successMessage = "Nombre actualizado") }
                loadUserProfile() // Recargar para asegurar consistencia
            }.onFailure { e ->
                _uiState.update { it.copy(isLoading = false, error = "Error al actualizar: ${e.message}") }
            }
        }
    }

    fun updatePassword(newPass: String, confirmPass: String) {
        if (newPass != confirmPass) {
            _uiState.update { it.copy(error = "Las contraseñas no coinciden") }
            return
        }
        val currentUser = _uiState.value.userProfile ?: return

        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val token = userSession.userToken.first() ?: ""
            val result = repository.updatePassword(token, currentUser.id, newPass)

            result.onSuccess {
                _uiState.update { it.copy(isLoading = false, successMessage = "Contraseña actualizada") }
            }.onFailure { e ->
                _uiState.update { it.copy(isLoading = false, error = "Error: ${e.message}") }
            }
        }
    }

    fun deleteAccount() {
        val currentUser = _uiState.value.userProfile ?: return
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val token = userSession.userToken.first() ?: ""
            val result = repository.deleteAccount(token, currentUser.id)

            result.onSuccess {
                userSession.clear() // Limpiar sesión local
                _uiState.update { it.copy(isLoading = false, isAccountDeleted = true) }
            }.onFailure { e ->
                _uiState.update { it.copy(isLoading = false, error = "No se pudo eliminar: ${e.message}") }
            }
        }
    }

    fun clearMessages() {
        _uiState.update { it.copy(error = null, successMessage = null) }
    }
}

// Factory simple
class ProfileViewModelFactory(
    private val repository: ProfileRepository,
    private val userSession: UserSession
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProfileViewModel(repository, userSession) as T
    }
}