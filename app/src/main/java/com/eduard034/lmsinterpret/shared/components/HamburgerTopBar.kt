package com.eduard034.lmsinterpret.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HamburgerTopBar(
    title: String,
    userName: String, // El nombre que viene del UserSession
    onLogoutClick: () -> Unit,
    onProfileClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        },
        actions = {
            // Botón de Hamburguesa / Menú
            IconButton(onClick = { expanded = true }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menú de opciones"
                )
            }

            // El Menú Desplegable
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.width(240.dp).background(Color.White)
            ) {
                // --- CABECERA DEL MENÚ (Datos del Usuario) ---
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(
                        text = "Hola,",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = userName, // Aquí se muestra el nombre dinámico
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }

                HorizontalDivider()

                // --- OPCIONES ---

                // Opción: Editar Perfil
                DropdownMenuItem(
                    text = { Text("Mi Perfil") },
                    onClick = {
                        expanded = false
                        onProfileClick()
                    },
                    leadingIcon = {
                        Icon(Icons.Default.Person, contentDescription = null)
                    }
                )

                // Opción: Configuración
                DropdownMenuItem(
                    text = {
                        Column {
                            Text("Configuración")
                            Text("Ajustes de la app", fontSize = 10.sp, color = Color.Gray)
                        }
                    },
                    onClick = {
                        expanded = false
                        onSettingsClick()
                    },
                    leadingIcon = {
                        Icon(Icons.Default.Settings, contentDescription = null)
                    }
                )

                HorizontalDivider()

                // Opción: Cerrar Sesión (Roja)
                DropdownMenuItem(
                    text = { Text("Cerrar sesión", color = MaterialTheme.colorScheme.error) },
                    onClick = {
                        expanded = false
                        onLogoutClick()
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Default.ExitToApp,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.White // O el color de fondo que prefieras
        )
    )
}