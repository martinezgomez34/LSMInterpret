package com.eduard034.lmsinterpret.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.eduard034.lmsinterpret.core.data.local.UserSession
import com.eduard034.lmsinterpret.feature.interpret.di.InterpretModule
import com.eduard034.lmsinterpret.feature.interpret.presentation.screens.InterpretScreen
import com.eduard034.lmsinterpret.feature.interpret.presentation.screens.SenaDetailScreen
import com.eduard034.lmsinterpret.feature.login.di.LoginModule
import com.eduard034.lmsinterpret.feature.login.presentation.screens.LoginScreen
import com.eduard034.lmsinterpret.feature.login.presentation.screens.RegisterScreen
import com.eduard034.lmsinterpret.feature.profile.di.ProfileModule
import com.eduard034.lmsinterpret.feature.profile.presentation.ProfileScreen

@Composable
fun NavigationWrapper(
    interpretModule: InterpretModule,
    loginModule: LoginModule,
    profileModule: ProfileModule,
    userSession: UserSession
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.Login
    ) {

        composable<Screens.Login> {
            LoginScreen(
                factory = loginModule.provideLoginViewModelFactory(),
                onLoginSuccess = {
                    navController.navigate(Screens.Interpret) {
                        popUpTo<Screens.Login> { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Screens.Register)
                }
            )
        }

        composable<Screens.Register> {
            RegisterScreen(
                factory = loginModule.provideRegisterViewModelFactory(),
                onRegisterSuccess = {
                    navController.popBackStack()
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable<Screens.Profile> {
            ProfileScreen(
                factory = profileModule.provideProfileViewModelFactory(),
                onBackClick = { navController.popBackStack() },
                onLogoutForce = {
                    navController.navigate(Screens.Login) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable<Screens.Interpret> {
            InterpretScreen(
                factory = interpretModule.provideInterpretViewModelFactory(),
                userSession = userSession,
                onSenaClick = { sena ->
                    navController.navigate(Screens.Detail(nombreSena = sena.nombre))
                },
                onNavigateToProfile = {
                    navController.navigate(Screens.Profile)
                },
                onLogoutSuccess = {
                    navController.navigate(Screens.Login) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable<Screens.Detail> { backStackEntry ->
            val detail: Screens.Detail = backStackEntry.toRoute()

            SenaDetailScreen(
                nombreSena = detail.nombreSena,
                factory = interpretModule.provideSenaDetailViewModelFactory(),
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}