package com.example.proyecto.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.proyecto.ui.components.SharedScaffold

@Composable
fun EchoScreen(
    onTabSelected: (Int) -> Unit,
    selectedTab: Int,
    navController: NavController
) {
    val posts = listOf(
        Triple("Monserrate", "https://bogota.gov.co/sites/default/files/styles/1050px/public/2023-01/iglesia-monserrate-1.jpg", "La icónica montaña con vistas panorámicas de Bogotá."),
        Triple("La Candelaria", "https://i.revistalternativa.com/cms/2023/11/18113937/La-Candelaria.jpg?r=1_1", "El corazón histórico de la ciudad, con calles coloridas y cultura."),
        Triple("Parque Simón Bolívar", "https://images.adsttc.com/media/images/5c17/0d02/08a5/e516/a300/006b/newsletter/Bargut_nueva.jpg?1545014498", "El pulmón verde de Bogotá, ideal para caminar y hacer deporte."),
        Triple("Museo del Oro", "https://d3nmwx7scpuzgc.cloudfront.net/sites/default/files/media/image/museo-del-oro-mo-salas-exposicion-permanente-2022-640x400.jpg", "Uno de los museos más importantes de Colombia, con piezas prehispánicas."),
        Triple("Plaza de Bolívar", "https://cdn.colombia.com/sdi/2013/11/27/plaza-de-bolivar-714091.jpg", "El centro político y cultural de la ciudad."),
        Triple("Zona T", "https://cloudfront-us-east-1.images.arcpublishing.com/infobae/T6XMHHZHLNEYPHTAG7HG6R3OD4.jpeg", "Una de las mejores zonas para la vida nocturna y gastronomía."),
        Triple("Jardín Botánico", "https://images.adsttc.com/media/images/6080/d60e/e6cf/df01/64fc/4ad4/newsletter/dsc4524.jpg?1619056204", "Un espacio natural con gran diversidad de flora."),
        Triple("Usaquén", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTjP9Azz6r6CqVlAI0AZvc7PCnDUWcp5rex_g&s", "Un barrio tradicional con mercados artesanales y restaurantes."),
        Triple("Chorro de Quevedo", "https://images.hive.blog/p/8DAuGnTQCLptZgjHUrRAJGcW4y1D4A5QVJJ7zjzqqKdfVHSS6NapSCCAhET8AGStKpbEh72YGjcyDAVeCetw8EbCUpCJcxmXkUnekNHLCS8X66abRwkQJH7LP7kByG3DUfLC3nESxEniuVyX92oRHwXjnyZKturEEHR427sH54A?format=match&mode=fit", "Lugar emblemático con grafitis y cultura callejera."),
        Triple("Maloka", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRPh_PNIG1HuZSJVa4RADLdFRL-XUAGG2UCOA&s", "Centro interactivo de ciencia y tecnología.")
    )

    val users = listOf("JuanPerez", "AnaGomez", "CarlosRod", "LuisaF", "SantiagoM", "ValeriaP")

    SharedScaffold(selectedTab = selectedTab, onTabSelected = onTabSelected, navController = navController) {
        if (selectedTab == 0) {
            Text("Contenido del MAPA") // Placeholder
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(posts) { (location, imageUrl, caption) ->
                    val username = users.random()

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AccountCircle,
                                    contentDescription = "Usuario",
                                    modifier = Modifier.size(40.dp),
                                    tint = Color.Gray
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = username,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = Color.Black
                                    )
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.LocationOn,
                                            contentDescription = "Ubicación",
                                            tint = Color.Red,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Text(
                                            text = location,
                                            fontSize = 14.sp,
                                            color = Color.Gray
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            AsyncImage(
                                model = imageUrl,
                                contentDescription = "Imagen de $location",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = caption,
                                fontSize = 14.sp,
                                color = Color.DarkGray
                            )
                        }
                    }
                }
            }
        }
    }
}
