package com.example.cgpaclaculator.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cgpaclaculator.data.HistoryItem
import com.example.cgpaclaculator.ui.components.TrackerButton
import com.example.cgpaclaculator.ui.theme.TrackerPrimary
import com.example.cgpaclaculator.ui.theme.TrackerSubtle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    history: List<HistoryItem>,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Result History", fontWeight = FontWeight.Bold, color = Color.White) },
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
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = TrackerPrimary,
                    scrolledContainerColor = TrackerPrimary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            if (history.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize().height(400.dp), contentAlignment = Alignment.Center) {
                    Text("No results saved yet.", color = Color.Gray)
                }
            } else {
//                Text(
//                    text = "Historical Breakdown",
//                    style = MaterialTheme.typography.titleLarge,
//                    fontWeight = FontWeight.Bold
//                )
//
//                Spacer(modifier = Modifier.height(16.dp))

                // Result Table Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(TrackerPrimary, RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                        .padding(12.dp)
                ) {
                    Text("Session / Type", color = Color.White, modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
                    Text("Result", color = Color.White, fontWeight = FontWeight.Bold)
                }

                history.forEachIndexed { index, item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(if (index % 2 == 0) Color.Transparent else TrackerSubtle.copy(alpha = 0.3f))
                            .padding(12.dp)
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(item.title, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            Text(item.date, fontSize = 10.sp, color = Color.Gray)
                        }
                        Text(item.result, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TrackerPrimary)
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Performance Trend",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Real Trend Graph using Canvas
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .background(TrackerSubtle.copy(alpha = 0.2f), RoundedCornerShape(12.dp))
                        .padding(start = 40.dp, end = 16.dp, top = 16.dp, bottom = 24.dp) // Space for labels
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val points = history.mapNotNull { it.result.toDoubleOrNull() }
                        val yLabels = listOf(0.0, 1.0, 2.0, 3.0, 4.0)
                        
                        // Draw Grid Lines & Labels
                        yLabels.forEach { label ->
                            val y = size.height - (label / 4.0 * size.height).toFloat()
                            
                            // Horizontal Line
                            drawLine(
                                color = Color.Gray.copy(alpha = 0.2f),
                                start = androidx.compose.ui.geometry.Offset(0f, y),
                                end = androidx.compose.ui.geometry.Offset(size.width, y),
                                strokeWidth = 1.dp.toPx()
                            )
                        }

                        if (points.size >= 2) {
                            val max = 4.0
                            val min = 0.0
                            val stepX = size.width / (points.size - 1)
                            
                            val path = Path().apply {
                                points.forEachIndexed { i, p ->
                                    val x = i * stepX
                                    val y = size.height - ((p - min) / (max - min) * size.height).toFloat()
                                    if (i == 0) moveTo(x, y) else lineTo(x, y)
                                }
                            }
                            
                            // Main Line
                            drawPath(
                                path = path,
                                color = TrackerPrimary,
                                style = Stroke(width = 3.dp.toPx(), cap = androidx.compose.ui.graphics.StrokeCap.Round)
                            )

                            // Draw Points
                            points.forEachIndexed { i, p ->
                                val x = i * stepX
                                val y = size.height - ((p - min) / (max - min) * size.height).toFloat()
                                drawCircle(
                                    color = TrackerPrimary,
                                    radius = 5.dp.toPx(),
                                    center = androidx.compose.ui.geometry.Offset(x, y)
                                )
                                drawCircle(
                                    color = Color.White,
                                    radius = 2.dp.toPx(),
                                    center = androidx.compose.ui.geometry.Offset(x, y)
                                )
                            }

                            // Fill area
                            val fillPath = Path().apply {
                                addPath(path)
                                lineTo(size.width, size.height)
                                lineTo(0f, size.height)
                                close()
                            }
                            drawPath(
                                path = fillPath,
                                brush = Brush.verticalGradient(listOf(TrackerPrimary.copy(alpha = 0.3f), Color.Transparent))
                            )
                        }
                    }
                    
                    // Overlay Y Labels
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(start = 16.dp, top = 8.dp, bottom = 24.dp)
                            .offset(x = (-45).dp),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.End
                    ) {
                        listOf("4.0", "3.0", "2.0", "1.0", "0.0").forEach { label ->
                            Text(label, fontSize = 10.sp, color = Color.Gray)
                        }
                    }

                    if (history.size < 2) {
                        Text("Need at least 2 results for trend", modifier = Modifier.align(Alignment.Center), color = Color.Gray, fontSize = 12.sp)
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                var isExporting by remember { mutableStateOf(false) }
                val scope = rememberCoroutineScope()
                var exportStatus by remember { mutableStateOf<String?>(null) }

                // Simulated PDF Download
                Box(contentAlignment = Alignment.Center) {
                    TrackerButton(
                        text = if (isExporting) "" else "Export as Report (PDF)",
                        onClick = {
                            if (!isExporting) {
                                isExporting = true
                                scope.launch {
                                    kotlinx.coroutines.delay(2000)
                                    isExporting = false
                                    exportStatus = "Report saved to Downloads!"
                                    kotlinx.coroutines.delay(3000)
                                    exportStatus = null
                                }
                            }
                        }
                    )
                    if (isExporting) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                    }
                }
                
                exportStatus?.let {
                    Text(
                        it, 
                        color = Color(0xFF2E7D32), 
                        fontSize = 14.sp, 
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 8.dp).align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}

