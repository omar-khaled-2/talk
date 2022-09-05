package com.sphinx.talk.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Color(0xFF3F51B5),
    primaryVariant = Color(0xFF303F9F),
    secondary = Color(0xFF90CAF9),
    surface = Color(0xFF1E272D)
)

private val LightColorPalette = lightColors(
    primary = Color(0xFF3F51B5),
    primaryVariant = Color(0xFF303F9F),
    secondary = Color(0xFF90CAF9),

    surface = Color(0xFFEDEEEF),
    background = Color(0xFFFFFFFF)

    /* Other default colors to override
    background = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun TalkTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
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