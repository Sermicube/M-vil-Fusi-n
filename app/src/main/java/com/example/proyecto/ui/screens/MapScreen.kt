package com.example.proyecto.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.proyecto.ui.components.SharedScaffold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun mapScreen(
    onTabSelected: (Int) -> Unit,
    selectedTab: Int,
    navController: NavController
) {
    var selectedGroup by remember { mutableStateOf("Familia") }
    val groups = listOf("Familia", "Amigos", "Trabajo")
    var expanded by remember { mutableStateOf(false) }
    var hotZonesActive by remember { mutableStateOf(false) }

    SharedScaffold(selectedTab = selectedTab, onTabSelected = onTabSelected, navController = navController) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Imagen de mapa
            AsyncImage(
                model = "https://media.wired.com/photos/59269cd37034dc5f91bec0f1/master/pass/GoogleMapTA.jpg",
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Controles arriba
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Selector de grupo
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = selectedGroup,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Grupo") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                        modifier = Modifier
                            .weight(1f)
                            .menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        groups.forEach { group ->
                            DropdownMenuItem(
                                text = { Text(group) },
                                onClick = {
                                    selectedGroup = group
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Botón de zonas calientes
                IconButton(
                    onClick = { hotZonesActive = !hotZonesActive }
                ) {
                    Icon(
                        Icons.Default.FavoriteBorder,
                        contentDescription = "Activar zonas calientes",
                        tint = if (hotZonesActive) Color.Red else Color.Gray,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            // Indicador de posición
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .clip(CircleShape)
                    .background(Color.Blue)
                    .align(Alignment.Center)
            )

            if (hotZonesActive) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(Color.Red.copy(alpha = 0.5f))
                        .align(Alignment.Center)
                )
            }
        }
    }
}
