package com.example.cgpaclaculator.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cgpaclaculator.R
import com.example.cgpaclaculator.ui.components.CgpaCircle
import com.example.cgpaclaculator.ui.components.TrackerButton
import com.example.cgpaclaculator.ui.theme.TrackerPrimary

import com.example.cgpaclaculator.data.UserProfile
import androidx.compose.material.icons.filled.Settings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    profile: UserProfile,
    latestCgpa: Double = 0.0,
    onAddResult: () -> Unit,
    onViewHistory: () -> Unit,
    onSwitchDept: () -> Unit,
    onCalculateCgpa: () -> Unit,
    onConversion: (Boolean) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Student Dashboard",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = TrackerPrimary,
                    scrolledContainerColor = TrackerPrimary, // Match primary even when scrolled
                ),
                actions = {
                    IconButton(onClick = onSwitchDept) {
                        Icon(
                            Icons.Default.Settings,
                            contentDescription = "Switch Dept",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header Section with Gradient
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(
                        Brush.verticalGradient(
                            listOf(TrackerPrimary, TrackerPrimary.copy(alpha = 0.8f))
                        )
                    )
            )

            // Content Card
            Column(
                modifier = Modifier
                    .offset(y = (-40).dp)
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .background(Color.White, RoundedCornerShape(24.dp))
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // University Transparent Logo
                Image(
                    painter = painterResource(id = R.drawable.transparent_logo), 
                    contentDescription = "Hero Logo",
                    modifier = Modifier.size(80.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = profile.name,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = profile.department,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(32.dp))

                CgpaCircle(cgpa = latestCgpa)

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Good Standing",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = TrackerPrimary
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Grid of Actions
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        TrackerButton(
                            text = "Semester GPA",
                            onClick = onAddResult,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        TrackerButton(
                            text = "Cumulative CGPA",
                            onClick = onCalculateCgpa,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(modifier = Modifier.fillMaxWidth()) {
                        TrackerButton(
                            text = "Marks to GPA",
                            onClick = { onConversion(false) },
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        TrackerButton(
                            text = "GPA to Marks",
                            onClick = { onConversion(true) }, 
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                TextButton(onClick = onViewHistory) {
                    Text("View Saved History", color = TrackerPrimary, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}
