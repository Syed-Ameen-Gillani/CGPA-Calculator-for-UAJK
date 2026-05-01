package com.syed.cgpacalculator.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversionScreen(
    onBack: () -> Unit,
    startWithGpaToMarks: Boolean = false
) {
    var isGpaToMarks by remember { mutableStateOf(startWithGpaToMarks) }
    var inputVal by remember { mutableStateOf("") }
    var creditHours by remember { mutableStateOf("3") }
    
    var resultGpa by remember { mutableStateOf<Double?>(null) }
    var resultPercentage by remember { mutableStateOf<Double?>(null) }
    var resultMarks by remember { mutableStateOf<Double?>(null) }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        if (isGpaToMarks) "GPA to Marks" else "Marks to GPA", 
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ) 
                },
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
            // TabRow removed for single-mode focus

            Spacer(modifier = Modifier.height(32.dp))

            TrackerTextField(
                value = inputVal,
                onValueChange = { inputVal = it },
                label = if (isGpaToMarks) "Enter GPA" else "Obtained Marks",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(16.dp))

            TrackerDropdown(
                value = creditHours,
                onValueChange = { creditHours = it },
                label = "Credit Hours",
                options = listOf("1", "2", "3", "4", "5", "6")
            )

            Spacer(modifier = Modifier.height(32.dp))

            if (resultPercentage != null) {
                Surface(
                    color = TrackerPrimary.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                            if (!isGpaToMarks) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("Calculated GPA", style = MaterialTheme.typography.labelLarge)
                                    Text(
                                        text = String.format("%.2f", resultGpa),
                                        style = MaterialTheme.typography.displaySmall,
                                        fontWeight = FontWeight.Bold,
                                        color = TrackerPrimary
                                    )
                                }
                            } else {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("Approx Marks", style = MaterialTheme.typography.labelLarge)
                                    Text(
                                        text = String.format("%.0f", resultMarks),
                                        style = MaterialTheme.typography.displaySmall,
                                        fontWeight = FontWeight.Bold,
                                        color = TrackerPrimary
                                    )
                                    
                                }
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Percentage", style = MaterialTheme.typography.labelLarge)
                                Text(
                                    text = String.format("%.1f%%", resultPercentage),
                                    style = MaterialTheme.typography.displaySmall,
                                    fontWeight = FontWeight.Bold,
                                    color = TrackerPrimary
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
            }

            TrackerButton(
                text = "Convert Now",
                onClick = {
                    val input = inputVal.toDoubleOrNull() ?: 0.0
                    val ch = creditHours.toIntOrNull() ?: 3
                    val total = CalculationLogic.getTotalMarks(ch)
                    
                    AdsReward.showIfAvailable(context as Activity) {
                        if (isGpaToMarks) {
                            val p = CalculationLogic.getPercentageFromGpa(input)
                            resultPercentage = p
                            resultMarks = (p / 100) * total
                        } else {
                            val p = (input / total) * 100
                            resultPercentage = p
                            resultGpa = CalculationLogic.getGpaFromPercentage(p)
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = {
                inputVal = ""
                resultGpa = null
                resultPercentage = null
                resultMarks = null
            }) {
                Text("Clear All", color = Color.Gray)
            }
        }
    }
}
