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
import com.eduard034.lmsinterpret.feature.interpret.presentation.viewmodels.InterpretViewModel
import com.eduard034.lmsinterpret.feature.interpret.presentation.viewmodels.InterpretViewModelFactory
import com.eduard034.lmsinterpret.feature.interpret.domain.entities.Sena

@Composable
fun InterpretScreen(
    factory: InterpretViewModelFactory,
    onSenaClick: (Sena) -> Unit // Navegacion al detalle
) {
    // Inyeccion manual del ViewModel usando el Factory
    val viewModel: InterpretViewModel = viewModel(factory = factory)
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    var textInput by remember { mutableStateOf("") }

    val LightGrayBg = Color(0xFFF0F0F0)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp, vertical = 40.dp)
    ) {
        // Titulo
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

        // Input (TextField)
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

        // Resultados
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (state.error != null) {
            Text(text = "Error: ${state.error}", color = Color.Red)
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(state.items) { sena ->
                    SenaItemButton(sena = sena, onClick = { onSenaClick(sena) })
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
            text = sena.nombre, // Muestra "Hola" o "Buenos Dias"
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
    }
}