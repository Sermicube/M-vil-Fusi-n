package com.example.proyecto.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyecto.ProfileSetupScreen
import com.example.proyecto.ui.screens.CameraIntentScreen
import com.example.proyecto.ui.screens.EchoScreen
import com.example.proyecto.ui.screens.LogInScreen
import com.example.proyecto.ui.screens.RegisterScreen
import com.example.proyecto.ui.screens.MapScreen

@Composable
fun NavigationStack() {
    val navController = rememberNavController()
    var selectedTab by remember { mutableStateOf(0) }

    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(route = Screen.Login.route) {
            LogInScreen(navController = navController)
        }
        composable(route = Screen.Register.route) { // Agregar la pantalla de registro
            RegisterScreen(navController = navController)
        }
        composable(route = Screen.Home.route) {
            when (selectedTab) {
                0 -> MapScreen(onTabSelected = { selectedTab = it },navController = navController)
                1 -> EchoScreen(onTabSelected = { selectedTab = it }, navController = navController)
            }
        }
        composable(route = Screen.ProfileSetup.route) {
            ProfileSetupScreen(navController = navController)
        }
        composable(route = Screen.Camera.route) {
            CameraIntentScreen(navController=navController)
        }
    }

}


sealed class Screen(val route: String) {
    object Login : Screen("login_screen")
    object Home : Screen("home_screen")
    object Register : Screen("register_screen")
    object ProfileSetup : Screen("profile_setup_screen")
    object Camera : Screen("camera_screen")
}