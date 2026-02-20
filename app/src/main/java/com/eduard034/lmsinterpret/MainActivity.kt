package com.eduard034.lmsinterpret

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.eduard034.lmsinterpret.core.navigation.NavigationWrapper
import com.eduard034.lmsinterpret.ui.theme.LMSInterpretTheme

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LMSInterpretTheme {
                NavigationWrapper()
            }
        }
    }
}