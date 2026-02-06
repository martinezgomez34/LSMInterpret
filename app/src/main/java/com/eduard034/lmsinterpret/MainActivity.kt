package com.eduard034.lmsinterpret

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.compose.rememberNavController
import com.eduard034.lmsinterpret.core.di.AppContainer
import com.eduard034.lmsinterpret.core.navigation.NavigationWrapper
import com.eduard034.lmsinterpret.feature.interpret.di.InterpretModule
import com.eduard034.lmsinterpret.feature.interpret.presentation.screens.InterpretScreen
import com.eduard034.lmsinterpret.feature.interpret.presentation.screens.SenaDetailScreen
import com.eduard034.lmsinterpret.ui.theme.LMSInterpretTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appContainer = AppContainer(applicationContext)

        val interpretModule = InterpretModule(appContainer)

        setContent {
            NavigationWrapper(interpretModule = interpretModule)
        }
    }
}