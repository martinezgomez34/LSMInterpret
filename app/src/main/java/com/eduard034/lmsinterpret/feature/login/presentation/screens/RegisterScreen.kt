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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.eduard034.lmsinterpret.feature.login.presentation.components.AuthButtonColor
import com.eduard034.lmsinterpret.feature.login.presentation.components.AuthBgColor
import com.eduard034.lmsinterpret.feature.login.presentation.components.AuthTextField
import com.eduard034.lmsinterpret.feature.login.presentation.viewmodels.RegisterViewModel

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    onRegisterSuccess: () -> Unit,
    onBackClick: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    var username by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPass by remember { mutableStateOf("") }

    var passError by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            viewModel.resetState()
            onRegisterSuccess()
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
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = "←",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable { onBackClick() }
                    .padding(bottom = 16.dp)
            )
        }

        Text(
            text = "LSMInterpret",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "SIGN UP",
            fontSize = 14.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(16.dp))

        AuthTextField(value = username, onValueChange = { username = it }, label = "Username")
        Spacer(modifier = Modifier.height(8.dp))

        AuthTextField(value = correo, onValueChange = { correo = it }, label = "Correo")
        Spacer(modifier = Modifier.height(8.dp))

        AuthTextField(value = password, onValueChange = { password = it }, label = "Password", isPassword = true)
        Spacer(modifier = Modifier.height(8.dp))

        AuthTextField(value = confirmPass, onValueChange = { confirmPass = it }, label = "Confirm Password", isPassword = true)

        if (passError != null) {
            Text(
                text = passError!!,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (password != confirmPass) {
                    passError = "Las contraseñas no coinciden"
                } else if (password.isBlank() || correo.isBlank()) {
                    passError = "Complete todos los campos"
                } else {
                    passError = null
                    viewModel.register(username, correo, password)
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = AuthButtonColor),
            modifier = Modifier.fillMaxWidth(0.7f).height(50.dp),
            enabled = !state.isLoading
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
            } else {
                Text("Create account", color = Color.Black, fontSize = 16.sp)
            }
        }

        if (state.error != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = state.error ?: "",
                color = Color.Red,
                fontSize = 12.sp
            )
        }
    }
}