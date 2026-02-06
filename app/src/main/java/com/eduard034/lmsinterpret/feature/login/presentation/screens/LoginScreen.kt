package com.eduard034.lmsinterpret.feature.login.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.eduard034.lmsinterpret.feature.login.presentation.components.AuthButtonColor
import com.eduard034.lmsinterpret.feature.login.presentation.components.AuthBgColor
import com.eduard034.lmsinterpret.feature.login.presentation.components.AuthTextField
import com.eduard034.lmsinterpret.feature.login.presentation.viewmodels.LoginViewModel
import com.eduard034.lmsinterpret.feature.login.presentation.viewmodels.LoginViewModelFactory

@Composable
fun LoginScreen(
    factory: LoginViewModelFactory,
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    // 1. Obtenemos ESPECÍFICAMENTE el LoginViewModel
    val viewModel: LoginViewModel = viewModel(factory = factory)
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    var correo by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // 2. Efecto para navegar cuando el login es exitoso
    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            viewModel.resetState()
            onLoginSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AuthBgColor)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "LSMInterpret",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "LOGIN",
            fontSize = 14.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Inputs usando el componente compartido
        AuthTextField(
            value = correo,
            onValueChange = { correo = it },
            label = "Correo"
        )

        Spacer(modifier = Modifier.height(16.dp))

        AuthTextField(
            value = password,
            onValueChange = { password = it },
            label = "Password",
            isPassword = true
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Botón Login
        Button(
            onClick = { viewModel.login(correo, password) },
            colors = ButtonDefaults.buttonColors(containerColor = AuthButtonColor),
            modifier = Modifier.width(160.dp).height(50.dp),
            enabled = !state.isLoading
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color.White,
                    strokeWidth = 2.dp // Opcional: para que no se vea muy grueso al ser pequeño
                )
            } else {
                Text("Sign in", color = Color.Black, fontSize = 16.sp)
            }
        }

        // Mensaje de Error
        if (state.error != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = state.error ?: "",
                color = Color.Red,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        // Links inferiores
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Create Account",
                color = Color.Blue,
                modifier = Modifier.clickable { onNavigateToRegister() }
            )
            Text(
                text = "Forgot password?",
                color = Color.Blue,
                modifier = Modifier.clickable { /* TODO: Implementar */ }
            )
        }
    }
}