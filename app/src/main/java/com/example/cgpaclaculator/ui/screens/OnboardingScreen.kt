package com.example.cgpaclaculator.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cgpaclaculator.R
import com.example.cgpaclaculator.ui.components.TrackerButton
import com.example.cgpaclaculator.ui.components.TrackerDropdown
import com.example.cgpaclaculator.ui.components.TrackerTextField
import com.example.cgpaclaculator.ui.theme.TrackerPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(
    onComplete: (String, String) -> Unit,
    onBack: (() -> Unit)? = null,
    initialName: String = "",
    initialDept: String = "Computer Science"
) {
    var name by remember { mutableStateOf(initialName) }
    var department by remember { mutableStateOf(initialDept) }
    
    // Background Rotation Logic
    var currentBgIndex by remember { mutableStateOf(0) }
    val backgrounds = listOf(R.drawable.kac2, R.drawable.chnar)
    
    LaunchedEffect(Unit) {
        while(true) {
            kotlinx.coroutines.delay(5000) // Change image every 5 seconds
            currentBgIndex = (currentBgIndex + 1) % backgrounds.size
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Crossfading Background
        androidx.compose.animation.Crossfade(
            targetState = backgrounds[currentBgIndex],
            animationSpec = androidx.compose.animation.core.tween(durationMillis = 2000),
            label = "bg_fade"
        ) { imageRes ->
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        
        // Overlay for readability
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
        )

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                if (onBack != null) {
                    TopAppBar(
                        title = { Text("Settings", color = Color.White, fontWeight = FontWeight.Bold) },
                        navigationIcon = {
                            IconButton(
                                onClick = onBack,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .background(TrackerPrimary, CircleShape)
                            ) {
                                Icon(
                                    Icons.Default.ArrowBack, 
                                    contentDescription = "Back", 
                                    tint = Color.White
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent,
                            scrolledContainerColor = Color.Transparent
                        )
                    )
                }
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.transparent_logo),
                    contentDescription = "Hero Logo",
                    modifier = Modifier.size(120.dp)
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Text(
                    text = if (onBack != null) "Update Profile" else "Welcome to UAJK Tracker",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                
                Text(
                    text = "Personalize your experience",
                    color = Color.White.copy(alpha = 0.8f),
                    modifier = Modifier.padding(top = 8.dp)
                )

                Spacer(modifier = Modifier.height(48.dp))

                TrackerTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = "Full Name",
                    placeholder = "e.g. Syed Ameen",
                    labelColor = Color.White,
                    isError = name.isBlank()
                )

                Spacer(modifier = Modifier.height(16.dp))

                TrackerDropdown(
                    value = department,
                    onValueChange = { department = it },
                    label = "Department",
                    options = listOf("Computer Science", "Information Technology"),
                    labelColor = Color.White
                )

                Spacer(modifier = Modifier.height(48.dp))

                TrackerButton(
                    text = if (onBack != null) "Save Changes" else "Get Started",
                    enabled = name.isNotBlank(),
                    onClick = {
                        if (name.isNotBlank()) {
                            onComplete(name, department)
                        }
                    }
                )
            }
        }
    }
}
