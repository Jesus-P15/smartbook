package co.edu.cecar.smartbooks.ui.screen.libros.componentsLibros

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.edu.cecar.smartbooks.data.Constants.DatoCell
import co.edu.cecar.smartbooks.data.DataClass.libro.LibroResponse
import co.edu.cecar.smartbooks.ui.theme.RojoInstitucional

private val StockOkBg    = Color(0xFFE8F8F0)
private val StockOkText  = Color(0xFF0F6E56)
private val StockLowBg   = Color(0xFFFFF7ED)
private val StockLowText = Color(0xFF9A3412)
private val StockZeroBg  = Color(0xFFFADAD6)
private val StockZeroText= Color(0xFF993C1D)

@Composable
fun LibroCard(
    libro: LibroResponse,
    onEditar: (LibroResponse) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = libro.nombre,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                TipoBadge(tipo = libro.tipo.toString())
            }

            HorizontalDivider(thickness = 0.5.dp)

            Row(modifier = Modifier.fillMaxWidth()) {
                DatoCell(label = "Nivel", valor = libro.nivel, modifier = Modifier.weight(1f))
                VerticalDivider(modifier = Modifier.height(56.dp), thickness = 0.5.dp)
                DatoCell(label = "Edición", valor = libro.edicion, modifier = Modifier.weight(1f))
                VerticalDivider(modifier = Modifier.height(56.dp), thickness = 0.5.dp)
                DatoCell(label = "Lote", valor = libro.lote.toString(), modifier = Modifier.weight(1f))
            }

            HorizontalDivider(thickness = 0.5.dp)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StockPill(stock = libro.stockTotal)

                IconButton(
                    onClick = { onEditar(libro) },
                    modifier = Modifier.size(36.dp).clip(RoundedCornerShape(8.dp))
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar ${libro.nombre}",
                        tint = RojoInstitucional,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun TipoBadge(tipo: String) {
    val (bg, textColor) = when (tipo.lowercase()) {
        "workbook" -> Color(0xFFEEF2FF) to Color(0xFF3730A3)
        "textbook" -> Color(0xFFFFF7ED) to Color(0xFF9A3412)
        else       -> MaterialTheme.colorScheme.surfaceVariant to MaterialTheme.colorScheme.onSurfaceVariant
    }
    Surface(shape = RoundedCornerShape(20.dp), color = bg) {
        Text(
            text = tipo,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = textColor
        )
    }
}

@Composable
private fun StockPill(stock: Int) {
    val (bg, textColor, label) = when {
        stock == 0 -> Triple(StockZeroBg, StockZeroText, "Sin stock")
        stock <= 5 -> Triple(StockLowBg, StockLowText, "Stock: $stock")
        else       -> Triple(StockOkBg, StockOkText, "Stock: $stock")
    }
    Surface(shape = RoundedCornerShape(20.dp), color = bg) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = textColor
        )
    }
}