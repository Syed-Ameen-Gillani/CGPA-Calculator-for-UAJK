package com.example.cgpaclaculator.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.cgpaclaculator.ui.components.TrackerButton
import com.example.cgpaclaculator.ui.components.TrackerTextField
import com.example.cgpaclaculator.ui.theme.TrackerPrimary
import com.example.cgpaclaculator.logic.CalculationLogic

data class SemesterEntry(
    val gpa: String = "",
    val units: String = "15"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CgpaCalculatorScreen(
    onBack: () -> Unit,
    onSave: (String, String, String) -> Unit
) {
    val semesters = remember { mutableStateListOf(SemesterEntry(), SemesterEntry()) }
    var calculatedCgpa by remember { mutableStateOf<Double?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("CGPA Calculator", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier
                            .padding(8.dp)
                            .background(TrackerPrimary, CircleShape)
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    scrolledContainerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Enter your semester GPAs and total credit hours per semester.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            semesters.forEachIndexed { index, semester ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Sem ${index + 1}", modifier = Modifier.width(60.dp), fontWeight = FontWeight.Bold)
                    TrackerTextField(
                        value = semester.gpa,
                        onValueChange = { semesters[index] = semester.copy(gpa = it) },
                        label = "GPA",
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    TrackerTextField(
                        value = semester.units,
                        onValueChange = { semesters[index] = semester.copy(units = it) },
                        label = "Credits",
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    if (semesters.size > 1) {
                        IconButton(onClick = { semesters.removeAt(index) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Remove", tint = Color.Red)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            OutlinedButton(
                onClick = { semesters.add(SemesterEntry()) },
                modifier = Modifier.align(Alignment.Start),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = TrackerPrimary)
            ) {
                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Add Semester")
            }

            Spacer(modifier = Modifier.height(32.dp))

            if (calculatedCgpa != null) {
                Surface(
                    color = TrackerPrimary.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Cumulative GPA (CGPA)", style = MaterialTheme.typography.bodyMedium)
                        Text(
                            text = String.format("%.2f", calculatedCgpa),
                            style = MaterialTheme.typography.displaySmall,
                            fontWeight = FontWeight.Bold,
                            color = TrackerPrimary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = CalculationLogic.getOverallGrade(calculatedCgpa ?: 0.0),
                            style = MaterialTheme.typography.titleMedium,
                            color = TrackerPrimary,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            TrackerButton(
                text = "Calculate CGPA",
                onClick = {
                    val entries = semesters.map { 
                        (it.gpa.toDoubleOrNull() ?: 0.0) to (it.units.toIntOrNull() ?: 0)
                    }
                    val cgpa = CalculationLogic.calculateCgpa(entries)
                    calculatedCgpa = cgpa
                    onSave("Cumulative CGPA", String.format("%.2f", cgpa), "CGPA")
                }
            )
        }
    }
}
