package com.gargantua7.cams.gp.android.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = MainColor,
    primaryVariant = ContrastingColor,
    surface = Color.Black,
    onSurface = Color.LightGray,
    secondary = Color.DarkGray,
    background = Color(0xFF333333),
    onBackground = Color.White
)

private val LightColorPalette = lightColors(
    primary = MainColor,
    primaryVariant = ContrastingColor,
    surface = Color.White,
    onSurface = Color.DarkGray,
    secondary = Color.LightGray,
    background = Color(0xFFEEEEEE),
    onBackground = Color.Black

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun CAMSGPAndroidTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
