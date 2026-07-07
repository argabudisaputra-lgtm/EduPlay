package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme =
  darkColorScheme(
    primary = EduPrimaryDark,
    onPrimary = Color(0xFF0F0E17),
    secondary = EduSecondaryDark,
    onSecondary = Color(0xFF0F0E17),
    tertiary = EduTertiaryDark,
    background = DarkDashboardBg,
    onBackground = Color(0xFFFFEBF0),
    surface = Color(0xFF1C1B22),
    onSurface = Color(0xFFFFEBF0),
    surfaceVariant = Color(0xFF242235),
    onSurfaceVariant = Color(0xFFA5A1FF)
  )

private val LightColorScheme =
  lightColorScheme(
    primary = EduPrimary,
    onPrimary = Color.White,
    secondary = EduSecondary,
    onSecondary = Color.Black,
    tertiary = EduTertiary,
    background = SoftBlueBg,
    onBackground = Color(0xFF1B1B1F),
    surface = Color.White,
    onSurface = Color(0xFF1B1B1F),
    surfaceVariant = Color(0xFFEFEFFF),
    onSurfaceVariant = Color(0xFF6C63FF)
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  // Disable dynamic color so that the curated Natural Tones brand colors are consistently shown
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }

      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
