package com.syed.cgpacalculator.ui.screens

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
import com.syed.cgpacalculator.ui.components.TrackerButton
import com.syed.cgpacalculator.ui.components.TrackerDropdown
import com.syed.cgpacalculator.ui.components.TrackerTextField
import com.syed.cgpacalculator.ui.theme.TrackerPrimary
import com.syed.cgpacalculator.logic.CalculationLogic
import androidx.compose.ui.platform.LocalContext
import android.app.Activity
import com.syed.cgpacalculator.ads.AdsReward

data class CourseEntry(
    val name: String = "",
    val grade: String = "A",
    val units: String = "3"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddResultScreen(
    onBack: () -> Unit,
    onSave: (String, String, String) -> Unit // title, result, type
) {
    var semester by remember { mutableStateOf("1st Semester") }
    val courses = remember { mutableStateListOf(CourseEntry()) }
    var calculatedGpa by remember { mutableStateOf<Double?>(null) }
    
    val context = LocalContext.current
    
    val gradeScale = mapOf(
        "A" to 4.0, "A-" to 3.7, "B+" to 3.3, "B" to 3.0, "B-" to 2.7,
        "C+" to 2.3, "C" to 2.0, "C-" to 1.7, "D+" to 1.3, "D" to 1.0, "F" to 0.0
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("GPA Calculator", fontWeight = FontWeight.Bold, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = TrackerPrimary,
                    scrolledContainerColor = TrackerPrimary,
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
            TrackerDropdown(
                value = semester,
                onValueChange = { semester = it },
                label = "Semester",
                options = listOf("1st Semester", "2nd Semester", "3rd Semester", "4th Semester", "5th Semester", "6th Semester", "7th Semester", "8th Semester", "9th Semester", "10th Semester")
            )

            Spacer(modifier = Modifier.height(24.dp))

            courses.forEachIndexed { index, course ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        TrackerTextField(
                            value = course.name,
                            onValueChange = { courses[index] = course.copy(name = it) },
                            label = "Course (Optional)"
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row {
                            TrackerDropdown(
                                value = course.grade,
                                onValueChange = { courses[index] = course.copy(grade = it) },
                                label = "Grade",
                                options = gradeScale.keys.toList(),
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            TrackerTextField(
                                value = course.units,
                                onValueChange = { courses[index] = course.copy(units = it) },
                                label = "Credits",
                                modifier = Modifier.weight(1f),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                        }
                    }
                    if (courses.size > 1) {
                        IconButton(onClick = { courses.removeAt(index) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Remove", tint = Color.Red)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            OutlinedButton(
                onClick = { courses.add(CourseEntry()) },
                modifier = Modifier.align(Alignment.Start),
                shape = RoundedCornerShape(8.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color.Transparent),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = TrackerPrimary)
            ) {
                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Add Course")
            }

            Spacer(modifier = Modifier.height(32.dp))

            if (calculatedGpa != null) {
                Surface(
                    color = TrackerPrimary.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Semester GPA", style = MaterialTheme.typography.bodyMedium)
                        Text(
                            text = String.format("%.2f", calculatedGpa),
                            style = MaterialTheme.typography.displaySmall,
                            fontWeight = FontWeight.Bold,
                            color = TrackerPrimary
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            TrackerButton(
                text = "Calculate GPA",
                onClick = {
                    val entries = courses.mapNotNull { 
                        val units = it.units.toIntOrNull()
                        if (units != null && units > 0) {
                            (gradeScale[it.grade] ?: 0.0) to units
                        } else null
                    }
                    if (entries.size == courses.size) {
                        AdsReward.showIfAvailable(context as Activity) {
                            val gpa = CalculationLogic.calculateSemesterGpa(entries)
                            calculatedGpa = gpa
                            onSave(semester, String.format("%.2f", gpa), "GPA")
                        }
                    }
                }
            )
        }
    }
}
