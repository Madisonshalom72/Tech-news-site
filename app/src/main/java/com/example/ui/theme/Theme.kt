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
    primary = TechPrimaryDark,
    onPrimary = TechOnPrimaryDark,
    primaryContainer = TechPrimaryContainerDark,
    onPrimaryContainer = TechOnPrimaryContainerDark,
    secondary = TechSecondaryDark,
    onSecondary = TechOnSecondaryDark,
    tertiary = TechTertiaryDark,
    onTertiary = TechOnTertiaryDark,
    background = TechSurfaceDark,
    surface = TechSurfaceDark,
    surfaceVariant = TechSurfaceVariantDark,
    onSurface = TechOnSurfaceDark,
    onSurfaceVariant = TechOnSurfaceVariantDark,
    surfaceContainerLow = TechSurfaceContainerLowDark,
    surfaceContainer = TechSurfaceContainerDark,
    surfaceContainerHigh = TechSurfaceContainerHighDark,
    outline = TechOutline,
  )

private val LightColorScheme =
  lightColorScheme(
    primary = TechPrimary,
    onPrimary = TechOnPrimary,
    primaryContainer = TechPrimaryContainer,
    onPrimaryContainer = TechOnPrimaryContainer,
    secondary = TechSecondary,
    onSecondary = TechOnSecondary,
    secondaryContainer = TechSecondaryContainer,
    onSecondaryContainer = TechOnSecondaryContainer,
    tertiary = TechTertiary,
    onTertiary = TechOnTertiary,
    tertiaryContainer = TechTertiaryContainer,
    onTertiaryContainer = TechOnTertiaryContainer,
    background = TechSurface,
    surface = TechSurface,
    surfaceVariant = TechSurfaceVariant,
    onSurface = TechOnSurface,
    onSurfaceVariant = TechOnSurfaceVariant,
    surfaceContainerLow = TechSurfaceContainerLow,
    surfaceContainer = TechSurfaceContainer,
    surfaceContainerHigh = TechSurfaceContainerHigh,
    outline = TechOutline,
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false, // Use our brand colors for cohesive TechNews identity
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
