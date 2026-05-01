package com.syed.cgpacalculator.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val EducationPrimary = Color(0xFF0D47A1)
val EducationBackground = Color(0xFFE3F2FD)
val EducationOnSurface = Color(0xFF0D1B2A)
val EducationSecondary = Color(0xFFFFCA28)
val EducationOnPrimary = Color(0xFFFFFFFF)
val EducationOnSecondary = Color(0xFF000000)
val EducationSurface = Color(0xFFFFFFFF)

// Tracker Modern Palette
val TrackerPrimary = Color(0xFF038DD6)
val TrackerBackground = Color(0xFFFFFFFF)
val TrackerSurface = Color(0xFFFFFFFF)
val TrackerOnSurface = Color(0xFF0F172A)
val TrackerSubtle = Color(0xFFF0F9FF)
val TrackerBorder = Color(0xFFE0F2FE)
val LightGradient = Brush.linearGradient(
    0.0f to EducationBackground,
    500.0f to EducationBackground,
    start = Offset.Zero,
    end = Offset.Infinite
)

val DarkGradient = Brush.linearGradient(
    0.0f to Color(0xFF0B1D3F),
    500.0f to Color(0xFF09233F),
    start = Offset.Zero,
    end = Offset.Infinite
)

val TransparentGradient = Brush.linearGradient(
    0.0f to Color.Transparent,
    500.0f to Color.Transparent,
    start = Offset.Zero,
    end = Offset.Infinite
)
val ColorScheme.focusedTextFieldText
    @Composable
    get() = if (isSystemInDarkTheme()) EducationOnPrimary else EducationOnSurface

val ColorScheme.UnFocusedTextFieldText
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFF94A3B8) else Color(0xFF475569)

val ColorScheme.textFieldContainer
    @Composable
    get() = if (isSystemInDarkTheme()) DarkGradient else LightGradient

val ColorScheme.buttonContainer
    @Composable
    get() = if (isSystemInDarkTheme()) EducationPrimary.copy(alpha = 0.9f) else EducationPrimary

val ColorScheme.dialogBoxButtonColor
    @Composable
    get() = if (isSystemInDarkTheme()) EducationPrimary.copy(alpha = 0.9f) else Color.Transparent
