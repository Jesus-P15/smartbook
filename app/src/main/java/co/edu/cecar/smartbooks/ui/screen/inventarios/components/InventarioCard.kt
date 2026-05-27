package co.edu.cecar.smartbooks.ui.screen.inventarios.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.edu.cecar.smartbooks.data.DataClass.inventarios.InventarioResponse
import co.edu.cecar.smartbooks.ui.screen.StockBadge
import co.edu.cecar.smartbooks.ui.screen.VerdeTexto
import co.edu.cecar.smartbooks.ui.screen.stockColor


val Superficie      = Color(0xFFF8F8F8)

@Composable
fun InventarioCard(inv: InventarioResponse) {
    Card(
        modifier = Modifier
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
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            // Título + badge de stock
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier.weight(1f).padding(end = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    Text(
                        text = inv.nombreLibro,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 18.sp
                    )
                    Text(
                        text = "Nivel ${inv.nivelLibro} · Ed. ${inv.edicionLibro} · ${inv.tipoLibro}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                StockBadge(stock = inv.stockDisponible)
            }

            // Fila de números: Ingresados / Vendidos / Disponibles
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                NumeroCell(
                    label = "Ingresados",
                    valor = inv.cantidadIngresada,
                    colorValor = VerdeTexto,
                    modifier = Modifier.weight(1f)
                )
                NumeroCell(
                    label = "Vendidos",
                    valor = inv.cantidadVendida,
                    colorValor = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                NumeroCell(
                    label = "Disponibles",
                    valor = inv.stockDisponible,
                    colorValor = stockColor(inv.stockDisponible),
                    modifier = Modifier.weight(1f)
                )
            }

            // Tag de lote
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(Superficie)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "Lote ${inv.lote}",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}


// ── Card de resumen ────────────────────────────────────────────────────────────
@Composable
fun ResumenCard(
    titulo: String,
    valor: String,
    colorIcono: Color,
    colorFondo: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = CardDefaults.outlinedCardBorder()
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(colorFondo),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = colorIcono,
                    modifier = Modifier.size(16.dp)
                )
            }
            Text(
                text = valor,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = titulo,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 14.sp
            )
        }
    }
}
