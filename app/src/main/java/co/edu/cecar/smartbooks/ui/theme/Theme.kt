package co.edu.cecar.smartbooks.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color



val RojoInstitucional = Color(0xFFC0392B)
val AzulOscuro = Color(0xFF1A3A5C)
val GrisClaro = Color(0xFFF2F3F4)
val GrisOscuro = Color(0xFF2C3E50)
val Blanco = Color(0xFFFFFFFF)
val GrisMedio = Color(0xFFBDC3C7)

private val SmartBookColors  = lightColorScheme(
    primary = RojoInstitucional,
    secondary = AzulOscuro,
    background = Blanco,
    surface = GrisClaro,
    onPrimary = Blanco,
    onSecondary = Blanco,
    onBackground = GrisOscuro

)


@Composable
fun SmartBooksTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = SmartBookColors,
        typography = Typography,
        content = content
    )
}