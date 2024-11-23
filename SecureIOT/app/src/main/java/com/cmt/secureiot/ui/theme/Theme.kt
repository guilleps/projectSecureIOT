package com.cmt.secureiot.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val orange = Color(0XFFF7931E)
val green = Color(0XFF22c55e)
val black = Color(0xFF000000)
val blackDisabled = Color(0xFF575757)
val grey = Color(0xFFF3F0F0)
val white = Color(0xFFFFFFFF)
val whiteDisabled = Color(0xFFC5C5C5)
val placeholder = Color(0xFF313131)

val backgroundLight = Color(0xFFFFFFFF)
val backgroundDark = Color(0xFF000000)

private val DarkColorScheme = darkColorScheme(
    primary = orange,
    secondary = black,
    tertiary = grey,
    onPrimary = green,
    onSecondary = white,
    onTertiary = placeholder,
    background = backgroundLight,
    secondaryContainer = blackDisabled,
    onSecondaryContainer = whiteDisabled
)

private val LightColorScheme = lightColorScheme(
    primary = orange,
    secondary = white,
    tertiary = grey,
    onPrimary = green,
    onSecondary = black,
    onTertiary = placeholder,
    background = backgroundDark,
    secondaryContainer = blackDisabled,
    onSecondaryContainer = whiteDisabled


    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun SecureIOTTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }


    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}