package com.eduard034.lmsinterpret.feature.interpret.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.eduard034.lmsinterpret.feature.interpret.presentation.viewmodels.SenaDetailViewModel
import com.eduard034.lmsinterpret.feature.interpret.presentation.viewmodels.SenaDetailViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SenaDetailScreen(
    nombreSena: String, // nombre que recibimos de la pantalla anterior, lo ideal seria el id
    factory: SenaDetailViewModelFactory,
    onBackClick: () -> Unit
) {
    val viewModel: SenaDetailViewModel = viewModel(factory = factory)
    // cargamos los datos al iniciar
    LaunchedEffect(nombreSena) {
        viewModel.loadSena(nombreSena)
    }

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(state.sena?.nombre ?: "Detalle", fontWeight = FontWeight.SemiBold)
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                },
                actions = {
                    IconButton(onClick = { /* accion menu */ }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Opciones")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White //
                )
            )
        },
        containerColor = Color.White
    ) { paddingValues ->

        Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (state.sena != null) {
                val sena = state.sena!!

                Column(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .fillMaxSize()
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    // Imagenes lado a lado o Scroll si son muchas
                    // Usamos LazyRow para que si hay 3 imagenes, se pueda deslizar
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth().height(200.dp)
                    ) {
                        items(sena.img) { imgUrl ->
                            AsyncImage(
                                model = imgUrl,
                                contentDescription = sena.nombre,
                                modifier = Modifier
                                    .width(160.dp)
                                    .fillMaxHeight()
                                    .clip(RoundedCornerShape(16.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Titulo Principal
                    Text(
                        text = sena.nombre,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    // Categoria
                    Text(
                        text = sena.categoria,
                        fontSize = 16.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Descripcion Label
                    Text(
                        text = "Descripción:",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Cuerpo de descripcion
                    Text(
                        text = sena.descripcion,
                        fontSize = 16.sp,
                        color = Color(0xFF4A4A4A),
                        lineHeight = 24.sp
                    )
                }
            }
        }
    }
}