package tz.co.yesayasoftware.authdemo.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val LightColorPalette = lightColors(
    primary = PrimaryColor,
    primaryVariant = PrimaryColor,
    secondary = PrimaryColor
)

@Composable
fun AuthProjectTheme(
    content: @Composable() () -> Unit
) {
    val colors = LightColorPalette

    rememberSystemUiController().setSystemBarsColor(
        color = BackgroundColor,
        darkIcons = false
    )

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}