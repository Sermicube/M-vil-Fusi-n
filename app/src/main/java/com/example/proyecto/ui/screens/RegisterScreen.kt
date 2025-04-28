package com.example.proyecto.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyecto.navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController) {
    val auth: FirebaseAuth = Firebase.auth
    val database = Firebase.database.reference
    var mail by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1E88E5)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = "ECHO",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Registro",
                fontSize = 35.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.LightGray,
                    containerColor = Color.White
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = mail,
                onValueChange = { mail = it },
                label = { Text("Correo electrónico") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.LightGray,
                    containerColor = Color.White
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = pass,
                onValueChange = { pass = it },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.LightGray,
                    containerColor = Color.White
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    CoroutineScope(Dispatchers.Main).launch {
                        try {
                            auth.createUserWithEmailAndPassword(mail, pass)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        val user = auth.currentUser
                                        user?.let {
                                            val userData = hashMapOf(
                                                "nombreDeUsuario" to name,
                                                "fotoDePerfilUrl" to "", // Inicialmente vacía
                                                "correoElectronico" to mail
                                            )
                                            database.child("usuarios").child(it.uid)
                                                .setValue(userData)
                                                .addOnSuccessListener {
                                                    navController.navigate(Screen.ProfileSetup.route)
                                                }
                                                .addOnFailureListener { e ->
                                                    errorMessage = "Error al guardar los datos del usuario: ${e.message}"
                                                    // Considera mostrar un mensaje al usuario
                                                }
                                        }
                                    } else {
                                        errorMessage = task.exception?.message
                                    }
                                }
                        } catch (e: Exception) {
                            errorMessage = e.localizedMessage
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Crear cuenta", fontSize = 18.sp)
            }

            errorMessage?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = it, color = Color.Red)
            }
        }
    }
}