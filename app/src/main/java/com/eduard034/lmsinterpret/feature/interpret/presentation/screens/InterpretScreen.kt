package com.eduard034.lmsinterpret.feature.interpret.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.eduard034.lmsinterpret.core.data.local.UserSession
import com.eduard034.lmsinterpret.feature.interpret.presentation.viewmodels.InterpretViewModel
import com.eduard034.lmsinterpret.feature.interpret.presentation.viewmodels.InterpretViewModelFactory
import com.eduard034.lmsinterpret.feature.interpret.domain.entities.Sena
import com.eduard034.lmsinterpret.shared.components.HamburgerTopBar // Ajusta tu import si es necesario
import kotlinx.coroutines.launch

@Composable
fun InterpretScreen(
    factory: InterpretViewModelFactory,
    userSession: UserSession,
    onSenaClick: (Sena) -> Unit,
    onNavigateToProfile: () -> Unit, // <--- 1. NUEVO PARÁMETRO
    onLogoutSuccess: () -> Unit
) {
    val viewModel: InterpretViewModel = viewModel(factory = factory)
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val userName by userSession.userName.collectAsState(initial = "Usuario")
    val coroutineScope = rememberCoroutineScope()

    var textInput by remember { mutableStateOf("") }
    val LightGrayBg = Color(0xFFF0F0F0)

    Scaffold(
        topBar = {
            HamburgerTopBar(
                title = "LSM Interpret",
                userName = userName ?: "Usuario",
                onProfileClick = onNavigateToProfile, // <--- 2. CONECTAMOS AQUÍ
                onSettingsClick = { /* TODO: Navegar a config */ },
                onLogoutClick = {
                    coroutineScope.launch {
                        userSession.clear()
                        onLogoutSuccess()
                    }
                }
            )
        },
        containerColor = Color.White
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Traductor",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Escribe aquí lo que quieras decir en LSM",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(32.dp))

            TextField(
                value = textInput,
                onValueChange = {
                    textInput = it
                    viewModel.onTextChanged(it)
                },
                placeholder = { Text("Ejem: \"Hola, buenos dias\"", color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(LightGrayBg),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = LightGrayBg,
                    unfocusedContainerColor = LightGrayBg,
                    disabledContainerColor = LightGrayBg,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(32.dp))

            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (state.error != null) {
                Text(text = "Error: ${state.error}", color = Color.Red)
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(state.items) { sena ->
                        SenaItemButton(sena = sena, onClick = { onSenaClick(sena) })
                    }
                }
            }
        }
    }
}

@Composable
fun SenaItemButton(sena: Sena, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(Color(0xFFE0E0E0))
            .clickable { onClick() }
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = sena.nombre,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
    }
}