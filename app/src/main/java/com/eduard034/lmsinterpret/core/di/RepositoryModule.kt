package com.eduard034.lmsinterpret.core.di // Ajusta tu paquete si es necesario

import com.eduard034.lmsinterpret.feature.interpret.domain.repositories.InterpretRepository
import com.eduard034.lmsinterpret.feature.interpret.data.repositories.InterpretRepositoryImpl
import com.eduard034.lmsinterpret.feature.profile.data.repository.ProfileRepositoryImpl
import com.eduard034.lmsinterpret.feature.profile.domain.ProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindInterpretRepository(
        interpretRepositoryImpl: InterpretRepositoryImpl
    ): InterpretRepository

    @Binds
    abstract fun bindProfileRepository(
        profileRepositoryImpl: ProfileRepositoryImpl
    ): ProfileRepository
}