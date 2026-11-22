package com.sapulcreation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Subtitles
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Midnight = Color(0xFF080914)
private val Panel = Color(0xFF15182A)
private val Purple = Color(0xFF9B72FF)
private val Cyan = Color(0xFF42D9E8)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { SapulApp() }
    }
}

@Composable
private fun SapulApp() {
    var prompt by remember { mutableStateOf("") }
    var selectedTab by remember { mutableStateOf("Create") }
    MaterialTheme(colorScheme = darkColorScheme(
        background = Midnight, surface = Panel, primary = Purple, secondary = Cyan
    )) {
        Scaffold(containerColor = Midnight) { padding ->
            Column(
                modifier = Modifier.fillMaxSize()
                    .background(Brush.verticalGradient(listOf(Color(0xFF17132B), Midnight, Color(0xFF07131D))))
                    .padding(padding).padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text("SAPUL", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Black)
                        Text("CREATION  •  v1.1.4", color = Cyan, fontSize = 12.sp, letterSpacing = 2.sp)
                    }
                    Surface(color = Panel, shape = RoundedCornerShape(14.dp)) {
                        Icon(Icons.Default.AutoAwesome, null, tint = Purple,
                            modifier = Modifier.padding(14.dp).size(28.dp))
                    }
                }
                Text("From Imagination to Cinema.", color = Color.LightGray, fontSize = 14.sp)
                Surface(color = Panel, shape = RoundedCornerShape(22.dp), modifier = Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("CREATE YOUR SCENE", color = Cyan, fontWeight = FontWeight.Bold, letterSpacing = 1.5.sp)
                        OutlinedTextField(
                            value = prompt, onValueChange = { prompt = it },
                            modifier = Modifier.fillMaxWidth().heightIn(min = 130.dp),
                            placeholder = { Text("Describe the scene you imagine…") },
                            label = { Text("Cinematic prompt") }, minLines = 4,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Cyan, unfocusedBorderColor = Color(0xFF45465E),
                                focusedTextColor = Color.White, unfocusedTextColor = Color.White
                            )
                        )
                        Button(onClick = { selectedTab = "Create" }, modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = Purple),
                            shape = RoundedCornerShape(14.dp)) {
                            Icon(Icons.Default.AutoAwesome, null)
                            Spacer(Modifier.width(8.dp))
                            Text("PREPARE CREATION")
                        }
                        Text("Prompt workspace foundation. Connect a server-side video provider to enable generation.",
                            color = Color(0xFF9DA1B8), fontSize = 11.sp)
                    }
                }
                Text("WORKSPACE", color = Color.LightGray, fontWeight = FontWeight.Bold, letterSpacing = 1.5.sp)
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    FeatureCard("Video Editor", "Media3 playback foundation", Icons.Default.Movie, Modifier.weight(1f))
                    FeatureCard("Subtitles", "SRT import and validation", Icons.Default.Subtitles, Modifier.weight(1f))
                }
                Spacer(Modifier.weight(1f))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    listOf("Create", "Editor", "Library").forEach { tab ->
                        TextButton(onClick = { selectedTab = tab }) {
                            Text(tab, color = if (selectedTab == tab) Cyan else Color.Gray)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FeatureCard(
    title: String, subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier
) {
    Surface(color = Panel, shape = RoundedCornerShape(18.dp), modifier = modifier.height(116.dp)) {
        Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(icon, null, tint = Cyan)
            Text(title, color = Color.White, fontWeight = FontWeight.Bold)
            Text(subtitle, color = Color(0xFF9DA1B8), fontSize = 10.sp)
        }
    }
}
