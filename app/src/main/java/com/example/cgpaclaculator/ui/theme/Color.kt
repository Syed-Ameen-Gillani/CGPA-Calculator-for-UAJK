package com.example.cgpaclaculator.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Black = Color(0xFF000113)
val LightBlue = Color(0xFFF1F5F9)
val BlueGray = Color(0xFF334155)
val imageColor = Color(0xFFB3D3FF)
val orange = Color(0xFFFB6D00)
val buttonColor = Color(0xFF5E35B1)
val Whitegradient = Brush.linearGradient(
   0.0f to Color.Magenta,
    500.0f to Color.Cyan,
    start = Offset.Zero,
    end = Offset.Infinite
)

val Blackgradient = Brush.linearGradient(
    0.0f to Color.Black,
    500.0f to Color.Black,
    start = Offset.Zero,
    end = Offset.Infinite
)

val anotherGradient = Brush.linearGradient(
    0.0f to Color.Transparent,
    500.0f to Color.Transparent,
    start = Offset.Zero,
    end = Offset.Infinite
)
val ColorScheme.focusedTextFieldText
    @Composable
    get() = if (isSystemInDarkTheme()) Color.White else Color.Black

val ColorScheme.UnFocusedTextFieldText
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFF94A3B8) else Color(0xFF475569)

val ColorScheme.textFieldContainer
    @Composable
    get() = if (isSystemInDarkTheme()) BlueGray.copy(alpha = 0.6f) else LightBlue

val ColorScheme.buttonContainer
    @Composable
    get() = if (isSystemInDarkTheme()) BlueGray.copy(alpha = 0.7f) else Black

val ColorScheme.dialogBoxButtonColor
    @Composable
    get() = if (isSystemInDarkTheme()) BlueGray.copy(alpha = 0.7f) else Color.Transparent