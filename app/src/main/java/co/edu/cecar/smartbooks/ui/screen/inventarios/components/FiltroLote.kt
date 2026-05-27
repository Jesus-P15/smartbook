package co.edu.cecar.smartbooks.ui.screen.inventarios.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.edu.cecar.smartbooks.ui.screen.AzulBorde
import co.edu.cecar.smartbooks.ui.screen.AzulPrincipal
import co.edu.cecar.smartbooks.ui.screen.AzulSuave
import co.edu.cecar.smartbooks.ui.screen.AzulTexto
import co.edu.cecar.smartbooks.ui.screen.BordeLinea

@Composable
fun FiltroLoteCard(
    loteFiltro: String,
    loteSeleccionado: Int?,
    lotesSugeridos: List<Int>,
    onFiltroChange: (String) -> Unit,
    onSeleccionar: (Int) -> Unit,
    onLimpiar: () -> Unit
) {
    Card(
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
            Text(
                text = "FILTRAR POR LOTE",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Campo de búsqueda
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = loteFiltro,
                    onValueChange = onFiltroChange,
                    placeholder = {
                        Text("Ej: 20261", style = MaterialTheme.typography.bodySmall)
                    },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AzulPrincipal,
                        unfocusedBorderColor = BordeLinea
                    )
                )
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = AzulPrincipal),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Buscar",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text("Buscar", fontSize = 13.sp)
                }
            }

            // Chips de lotes sugeridos
            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                items(lotesSugeridos) { lote ->
                    val seleccionado = loteSeleccionado == lote
                    val year = lote / 10
                    val sem = lote % 10

                    FilterChip(
                        selected = seleccionado,
                        onClick = {
                            if (seleccionado) onLimpiar() else onSeleccionar(lote)
                        },
                        label = {
                            Text(
                                text = "Sem $sem · $year${if (seleccionado) " ✓" else ""}",
                                fontSize = 12.sp
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = AzulSuave,
                            selectedLabelColor = AzulTexto,
                            selectedLeadingIconColor = AzulTexto
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = seleccionado,
                            selectedBorderColor = AzulBorde,
                            borderColor = BordeLinea
                        )
                    )
                }
            }

            Text(
                text = "Formato: año + semestre — ej: 20261",
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
