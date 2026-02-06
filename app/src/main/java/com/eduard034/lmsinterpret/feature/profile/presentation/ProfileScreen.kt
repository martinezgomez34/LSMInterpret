package com.eduard034.lmsinterpret.feature.profile.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.eduard034.lmsinterpret.core.data.local.UserSession

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    factory: ProfileViewModelFactory,
    onBackClick: () -> Unit,
    onLogoutForce: () -> Unit // Llamado cuando se elimina la cuenta
) {
    val viewModel: ProfileViewModel = viewModel(factory = factory)
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    // Estados locales para los inputs
    var usernameInput by remember { mutableStateOf("") }
    var newPasswordInput by remember { mutableStateOf("") }
    var confirmPasswordInput by remember { mutableStateOf("") }
    var showDeleteDialog by remember { mutableStateOf(false) }

    // Sincronizar datos cuando cargan
    LaunchedEffect(state.userProfile) {
        state.userProfile?.let {
            usernameInput = it.username
        }
    }

    // Manejo de eventos (Toasts y Navegación)
    LaunchedEffect(state) {
        if (state.error != null) {
            Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
            viewModel.clearMessages()
        }
        if (state.successMessage != null) {
            Toast.makeText(context, state.successMessage, Toast.LENGTH_SHORT).show()
            // Limpiar campos de contraseña tras éxito
            newPasswordInput = ""
            confirmPasswordInput = ""
            viewModel.clearMessages()
        }
        if (state.isAccountDeleted) {
            Toast.makeText(context, "Cuenta eliminada. Hasta luego.", Toast.LENGTH_LONG).show()
            onLogoutForce()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Editar perfil") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Opciones extra si quieres */ }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Más")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
            )
        }
    ) { padding ->
        if (state.isLoading && state.userProfile == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp)
            ) {
                // --- SECCIÓN DATOS ---
                Text("Datos del perfil", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(24.dp))

                // Username
                OutlinedTextField(
                    value = usernameInput,
                    onValueChange = { usernameInput = it },
                    label = { Text("Username") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Correo (Solo lectura)
                OutlinedTextField(
                    value = state.userProfile?.correo ?: "",
                    onValueChange = {},
                    label = { Text("Correo") },
                    enabled = false, // Deshabilitado como en la imagen
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledTextColor = Color.Gray,
                        disabledBorderColor = Color.LightGray
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Fake Password Field (Visual)
                OutlinedTextField(
                    value = "********",
                    onValueChange = {},
                    label = { Text("Password") },
                    enabled = false,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    trailingIcon = {
                        Button(
                            onClick = { viewModel.updateUsername(usernameInput) },
                            shape = RoundedCornerShape(20.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            modifier = Modifier.height(36.dp)
                        ) {
                            Text("Guardar", fontSize = 12.sp)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(40.dp))

                // --- SECCIÓN PASSWORD ---
                Text("Cambiar contraseña", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = newPasswordInput,
                    onValueChange = { newPasswordInput = it },
                    label = { Text("NewPassword") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = confirmPasswordInput,
                    onValueChange = { confirmPasswordInput = it },
                    label = { Text("ConfirmNewPassword") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Botón Guardar Password (Alineado a la derecha)
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                    Button(
                        onClick = { viewModel.updatePassword(newPasswordInput, confirmPasswordInput) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF333333))
                    ) {
                        Text("Guardar cambios")
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                // Botón Eliminar
                Button(
                    onClick = { showDeleteDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE57373)) // Rojo claro
                ) {
                    Text("Eliminar perfil", color = Color.White)
                }
            }
        }
    }

    // --- DIÁLOGO DE ELIMINACIÓN ---
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = {
                Text(
                    text = "ELIMINAR PERFIL",
                    color = Color(0xFFB00020),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            },
            text = {
                Column {
                    Text("Usted desea eliminar su perfil? Esta acción eliminará todos los datos de su cuenta y no se podrán recuperar.")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "UserName: ${state.userProfile?.username}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDeleteDialog = false
                        viewModel.deleteAccount()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B0000)) // Rojo oscuro
                ) {
                    Text("Confirmar")
                }
            },
            dismissButton = {
                // Opcional: Botón cancelar si quieres
            },
            containerColor = Color.White,
            shape = RoundedCornerShape(16.dp)
        )
    }
}