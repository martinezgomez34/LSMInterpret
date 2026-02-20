package com.eduard034.lmsinterpret.feature.login.di

import com.eduard034.lmsinterpret.feature.login.data.repositories.UserRepositoryImpl
import com.eduard034.lmsinterpret.feature.login.domain.repositories.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LoginRepositoryModule {

    @Binds
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository
}