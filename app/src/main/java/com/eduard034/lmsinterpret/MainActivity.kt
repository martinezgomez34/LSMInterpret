package com.eduard034.lmsinterpret

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.eduard034.lmsinterpret.core.di.AppContainer
import com.eduard034.lmsinterpret.core.navigation.NavigationWrapper
import com.eduard034.lmsinterpret.feature.interpret.di.InterpretModule
import com.eduard034.lmsinterpret.feature.login.di.LoginModule
import com.eduard034.lmsinterpret.feature.profile.di.ProfileModule
import com.eduard034.lmsinterpret.feature.profile.presentation.ProfileViewModelFactory
import com.eduard034.lmsinterpret.ui.theme.LMSInterpretTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appContainer = AppContainer(applicationContext)

        val interpretModule = InterpretModule(appContainer)
        val loginModule = LoginModule(appContainer)
        val profileModule = ProfileModule(appContainer)
        setContent {
            LMSInterpretTheme {
                NavigationWrapper(
                    interpretModule = interpretModule,
                    loginModule = loginModule,
                    profileModule = profileModule,
                    userSession = appContainer.userSession
                )
            }
        }
    }
}