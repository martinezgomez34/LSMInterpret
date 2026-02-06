package com.eduard034.lmsinterpret.feature.interpret.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eduard034.lmsinterpret.feature.interpret.domain.repositories.InterpretRepository

class SenaDetailViewModelFactory(
    private val repository: InterpretRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SenaDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SenaDetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}