package com.example.proyecto.ui.components

import android.app.Activity
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.proyecto.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharedScaffold(navController: NavController,
                   selectedTab: Int, onTabSelected: (Int) -> Unit,
                   content: @Composable () -> Unit,
                   ) {
    val takePictureLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageBitmap = result.data?.extras?.get("data") as Bitmap
            // Aquí puedes manejar la imagen capturada
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Barra superior
        TopAppBar(
            title = {
                Text(
                    text = "ECHO",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFF1E88E5),
                titleContentColor = Color.White
            )
        )

        // Tabs de navegación
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = Color(0xFF1E88E5),
            contentColor = Color.White,
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { onTabSelected(0) },
                text = { Text("MAPA") }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { onTabSelected(1) },
                text = { Text("FEED") }
            )
        }

        // Contenido dinámico
        Box(modifier = Modifier.weight(1f)) {
            content()
        }

        // Botón flotante para abrir la cámara
        NavigationBar(modifier = Modifier.fillMaxWidth()) {
            NavigationBarItem(
                icon = { Icon(Icons.Default.Home, contentDescription = "Mapa") },
                selected = false,
                onClick = {}
            )
            NavigationBarItem(
                icon = { Icon(Icons.Default.Favorite, contentDescription = "Feed") },
                selected = false,
                onClick = {}
            )
            NavigationBarItem(
                icon = { Icon(Icons.Default.Add, contentDescription = "Tomar Foto") },
                selected = false,
                onClick = {
                    navController.navigate(Screen.Camera.route)
                }
            )
            NavigationBarItem(
                icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
                selected = false,
                onClick = {}
            )
        }
    }
}