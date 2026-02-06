package com.eduard034.lmsinterpret.feature.interpret.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eduard034.lmsinterpret.feature.interpret.domain.usecases.InterpretarUseCase

class InterpretViewModelFactory(
    private val interpretarUseCase: InterpretarUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InterpretViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InterpretViewModel(interpretarUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}