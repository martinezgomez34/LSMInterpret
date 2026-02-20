package com.eduard034.lmsinterpret.feature.interpret.di

import com.eduard034.lmsinterpret.core.di.NewtworkModule
import com.eduard034.lmsinterpret.feature.interpret.domain.usecases.InterpretarUseCase

class InterpretModule(
    private val appContainer: NewtworkModule
) {
    private fun provideInterpretarUseCase(): InterpretarUseCase {
        return InterpretarUseCase(appContainer.interpretRepository)
    }

    fun provideInterpretViewModelFactory(): InterpretViewModelFactory {
        return InterpretViewModelFactory(
            interpretarUseCase = provideInterpretarUseCase()
        )
    }

    fun provideSenaDetailViewModelFactory(): SenaDetailViewModelFactory {
        return SenaDetailViewModelFactory(appContainer.interpretRepository)
    }
}