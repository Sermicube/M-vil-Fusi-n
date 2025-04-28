    package com.example.proyecto.ui.screens

    import androidx.compose.foundation.Image
    import androidx.compose.foundation.clickable
    import androidx.compose.foundation.layout.*
    import androidx.compose.foundation.shape.CircleShape
    import androidx.compose.material3.*
    import androidx.compose.runtime.*
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.draw.clip
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.layout.ContentScale
    import androidx.compose.ui.res.painterResource
    import androidx.compose.ui.unit.dp
    import androidx.navigation.NavController
    import coil3.compose.AsyncImage
    import com.example.proyecto.navigation.Screen
    import com.google.firebase.auth.FirebaseAuth
    import com.google.firebase.database.ktx.database
    import com.google.firebase.ktx.Firebase
    import com.example.proyecto.R
    import com.example.proyecto.ui.components.ImagePicker
    import kotlinx.coroutines.CoroutineScope
    import kotlinx.coroutines.Dispatchers
    import kotlinx.coroutines.launch

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ProfileSetupScreen(navController: NavController) {
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val userId = currentUser?.uid
        val database = Firebase.database.reference

        var username by remember { mutableStateOf("") }
        var profilePictureUrl by remember { mutableStateOf("") }

        var showImagePicker by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            if (profilePictureUrl.isNotEmpty()) {
                AsyncImage(
                    model = profilePictureUrl,
                    contentDescription = "Foto de perfil",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .clickable { showImagePicker = true },
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
                        .clip(CircleShape)
                        .clickable { showImagePicker = true },
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Nombre de usuario") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    userId?.let { uid ->
                        val userData = hashMapOf(
                            "nombreDeUsuario" to username,
                            "fotoDePerfilUrl" to profilePictureUrl
                        )
                        database.child("usuarios").child(uid)
                            .setValue(userData)
                            .addOnSuccessListener {
                                navController.navigate(Screen.Home.route) {
                                    popUpTo(navController.graph.id) { inclusive = true }
                                }
                            }
                            .addOnFailureListener { e ->
                                println("Error al guardar el perfil: ${e.message}")
                            }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Perfil")
            }
        }

        if (showImagePicker) {
            ImagePicker { downloadUrl ->
                profilePictureUrl = downloadUrl
                showImagePicker = false


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
