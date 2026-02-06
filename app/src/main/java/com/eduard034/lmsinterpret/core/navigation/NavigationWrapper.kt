package com.eduard034.lmsinterpret.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.eduard034.lmsinterpret.feature.interpret.di.InterpretModule
import com.eduard034.lmsinterpret.feature.interpret.presentation.screens.InterpretScreen
import com.eduard034.lmsinterpret.feature.interpret.presentation.screens.SenaDetailScreen

@Composable
fun NavigationWrapper(
    interpretModule: InterpretModule // Recibimos el módulo para inyectar dependencias
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.Interpret // Usamos la ruta definida en Core
    ) {

        // --- Feature: Interpret ---

        composable<Screens.Interpret> {
            InterpretScreen(
                factory = interpretModule.provideInterpretViewModelFactory(),
                onSenaClick = { sena ->
                    // Navegamos usando el objeto definido en Core
                    navController.navigate(Screens.Detail(nombreSena = sena.nombre))
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

        // --- Aquí irían otras features en el futuro (Login, Perfil, etc) ---
    }
}