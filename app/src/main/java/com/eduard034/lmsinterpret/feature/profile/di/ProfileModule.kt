package com.eduard034.lmsinterpret.feature.profile.di

import com.eduard034.lmsinterpret.core.di.NewtworkModule
import com.eduard034.lmsinterpret.feature.profile.presentation.ProfileViewModelFactory

class ProfileModule(private val appContainer: NewtworkModule) {

    fun provideProfileViewModelFactory(): ProfileViewModelFactory {
        return ProfileViewModelFactory(
            repository = appContainer.profileRepository, // Asegúrate de tener esto en AppContainer
            userSession = appContainer.userSession
        )
    }
}