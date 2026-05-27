package co.edu.cecar.smartbooks.data.Constants

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


object SmartColors {
    val AzulPrincipal  = Color(0xFF185FA5)
    val AzulSuave      = Color(0xFFE6F1FB)
    val AzulBorde      = Color(0xFFB5D4F4)
    val AzulTexto      = Color(0xFF0C447C)

    val VerdeRelleno   = Color(0xFFEAF3DE)
    val VerdeTexto     = Color(0xFF3B6D11)

    val AmbarRelleno   = Color(0xFFFAEEDA)
    val AmbarTexto     = Color(0xFF854F0B)

    val RojoRelleno    = Color(0xFFFCEBEB)
    val RojoTexto      = Color(0xFFA32D2D)

    val CoralSuave     = Color(0xFFFAECE7)
    val CoralTexto     = Color(0xFF993C1D)

    val Superficie     = Color(0xFFF8F8F8)
    val BordeLinea     = Color(0xFFE8E8E8)
}


@Composable
fun DatoCell(
    label: String,
    valor: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = label.uppercase(),
            fontSize = 10.sp,
            letterSpacing = 0.5.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = valor,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}


@Composable
fun EstadoBadge(
    texto: String,
    colorFondo: Color,
    colorTexto: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(colorFondo)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = texto,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = colorTexto
        )
    }
}

@Composable
fun BadgeExito(texto: String, modifier: Modifier = Modifier) =
    EstadoBadge(texto, SmartColors.VerdeRelleno, SmartColors.VerdeTexto, modifier)

@Composable
fun BadgeAdvertencia(texto: String, modifier: Modifier = Modifier) =
    EstadoBadge(texto, SmartColors.AmbarRelleno, SmartColors.AmbarTexto, modifier)

@Composable
fun BadgeError(texto: String, modifier: Modifier = Modifier) =
    EstadoBadge(texto, SmartColors.RojoRelleno, SmartColors.RojoTexto, modifier)

@Composable
fun BadgeInfo(texto: String, modifier: Modifier = Modifier) =
    EstadoBadge(texto, SmartColors.AzulSuave, SmartColors.AzulTexto, modifier)


//  SeccionCard
@Composable
fun SeccionCard(
    modifier: Modifier = Modifier,
    padding: Int = 14,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = CardDefaults.outlinedCardBorder()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            content = content
        )
    }
}



//  4. SeccionHeader

@Composable
fun SeccionHeader(
    titulo: String,
    contador: String? = null,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = titulo.uppercase(),
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        if (contador != null) {
            Text(
                text = contador,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

//  ErrorCard


@Composable
fun ErrorCard(
    mensaje: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SmartColors.RojoRelleno),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = androidx.compose.ui.graphics.SolidColor(
                SmartColors.RojoTexto.copy(alpha = 0.3f)
            )
        )
    ) {
        Text(
            text = mensaje,
            modifier = Modifier.padding(14.dp),
            fontSize = 13.sp,
            color = SmartColors.RojoTexto,
            lineHeight = 18.sp
        )
    }
}

//    EstadoVacio


@Composable
fun EstadoVacio(
    emoji: String,
    mensaje: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(emoji, fontSize = 36.sp)
            Text(
                text = mensaje,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}


//   CargandoIndicador
@Composable
fun CargandoIndicador(
    modifier: Modifier = Modifier,
    color: Color = SmartColors.AzulPrincipal
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = color,
            strokeWidth = 2.dp
        )
    }
}


//   FilaDato


@Composable
fun FilaDato(
    label: String,
    valor: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = valor,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}


//   HandleSheet


@Composable
fun HandleSheet(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .width(40.dp)
            .height(4.dp)
            .clip(RoundedCornerShape(2.dp))
            .background(SmartColors.BordeLinea)
    )
}