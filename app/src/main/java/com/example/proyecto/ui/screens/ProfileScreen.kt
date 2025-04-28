package com.example.proyecto.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyecto.R
import coil3.compose.AsyncImage
import com.example.proyecto.navigation.Screen
import com.example.proyecto.ui.components.ImagePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

@Composable
fun ProfileScreen(navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser
    val userId = currentUser?.uid
    val database = Firebase.database.reference

    val displayName = remember { mutableStateOf("Cargando...") }
    val profilePictureUrl = remember { mutableStateOf("") }
    val userEmail = remember { mutableStateOf(currentUser?.email ?: "") }

    var showImagePicker by remember { mutableStateOf(false) }

    LaunchedEffect(userId) {
        userId?.let { uid ->
            val userRef = database.child("usuarios").child(uid)
            userRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        displayName.value = snapshot.child("nombreDeUsuario").getValue(String::class.java) ?: "Usuario Anónimo"
                        profilePictureUrl.value = snapshot.child("fotoDePerfilUrl").getValue(String::class.java) ?: ""
                    } else {
                        displayName.value = "Usuario no encontrado"
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    displayName.value = "Error al cargar el perfil"
                }
            })
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        // Foto de perfil
        if (profilePictureUrl.value.isNotEmpty()) {
            AsyncImage(
                model = profilePictureUrl.value,
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.ic_profile_placeholder),
                error = painterResource(id = R.drawable.ic_profile_placeholder)
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.ic_profile_placeholder),
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = displayName.value,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                showImagePicker = true
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Filled.Edit, contentDescription = "Editar perfil")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Editar Perfil")
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                auth.signOut()
                navController.navigate(Screen.Login.route) {
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cerrar Sesión", color = Color.White)
        }
    }

    if (showImagePicker) {
        ImagePicker { downloadUrl ->
            profilePictureUrl.value = downloadUrl
            showImagePicker = false

            //  Aquí actualizamos en Firebase Database directamente
            userId?.let { uid ->
                val database = Firebase.database.reference
                val userRef = database.child("usuarios").child(uid)

                val updates = mapOf<String, Any>(
                    "fotoDePerfilUrl" to downloadUrl
                )

                userRef.updateChildren(updates)
            }
        }
    }

}
