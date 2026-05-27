package co.edu.cecar.smartbooks.ui.screen.inventarios.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ── Celda de número (Ingresados / Vendidos / Disponibles) ──────────────────────
@Composable
fun NumeroCell(
    label: String,
    valor: Int,
    colorValor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(Superficie)
            .padding(horizontal = 8.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Text(
            text = label.uppercase(),
            fontSize = 10.sp,
            letterSpacing = 0.4.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = valor.toString(),
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = colorValor
        )
    }
}

