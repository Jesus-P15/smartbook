package co.edu.cecar.smartbooks.ui.screen.ventas.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.edu.cecar.smartbooks.data.Constants.SmartColors
import co.edu.cecar.smartbooks.data.DataClass.libro.LibroResponse
import co.edu.cecar.smartbooks.viewmodel.ItemVentaUi
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemVentaCard(
    item: ItemVentaUi,
    index: Int,
    libros: List<LibroResponse>,
    onItemChange: (ItemVentaUi) -> Unit,
    onEliminar: () -> Unit,
    mostrarEliminar: Boolean
) {
    var libroExpanded by remember { mutableStateOf(false) }

    val libroSeleccionado = libros.find { it.id == item.libroId }
    val formatter = NumberFormat.getCurrencyInstance(Locale("es", "CO"))
    val subtotal = (libroSeleccionado?.valorVentaPublico?.toDouble() ?: 0.0) * item.cantidad

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SmartColors.Superficie),
        border = CardDefaults.outlinedCardBorder()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Cabecera ítem + botón eliminar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Ítem ${index + 1}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                if (mostrarEliminar) {
                    IconButton(
                        onClick = onEliminar,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Eliminar ítem",
                            tint = SmartColors.RojoTexto,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            // ── Dropdown Libro ────────────────────────────────────────────
            ExposedDropdownMenuBox(
                expanded = libroExpanded,
                onExpandedChange = { libroExpanded = it }
            ) {
                OutlinedTextField(
                    value = libroSeleccionado?.nombre ?: "Selecciona un libro",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Libro", fontSize = 11.sp) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(libroExpanded) },
                    modifier = Modifier
                        .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SmartColors.AzulPrincipal,
                        unfocusedBorderColor = SmartColors.BordeLinea
                    )
                )
                ExposedDropdownMenu(
                    expanded = libroExpanded,
                    onDismissRequest = { libroExpanded = false }
                ) {
                    if (libros.isEmpty()) {
                        DropdownMenuItem(
                            text = { Text("Cargando libros…", fontSize = 13.sp) },
                            onClick = {}
                        )
                    } else {
                        libros.forEach { libro ->
                            DropdownMenuItem(
                                text = {
                                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                        Text(
                                            libro.nombre,
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                            Text(
                                                "Stock: ${libro.stockTotal}",
                                                fontSize = 11.sp,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                            Text(
                                                "Lote: ${libro.lote}",
                                                fontSize = 11.sp,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                            Text(
                                                formatter.format(libro.valorVentaPublico.toDouble()),
                                                fontSize = 11.sp,
                                                color = SmartColors.AzulPrincipal
                                            )
                                        }
                                    }
                                },
                                onClick = {
                                    // Al elegir el libro se asigna su lote automáticamente
                                    onItemChange(item.copy(libroId = libro.id, lote = libro.lote))
                                    libroExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            // ── Info lote asignado + campo cantidad ───────────────────────
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Badge lote (solo informativo)
                if (libroSeleccionado != null) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = SmartColors.AzulPrincipal.copy(alpha = 0.1f),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Text(
                                "LOTE ASIGNADO",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp,
                                color = SmartColors.AzulPrincipal
                            )
                            Text(
                                libroSeleccionado.lote.toString(),
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = SmartColors.AzulPrincipal
                            )
                        }
                    }
                } else {
                    Spacer(Modifier.weight(1f))
                }

                // Campo cantidad
                OutlinedTextField(
                    value = item.cantidad.toString(),
                    onValueChange = { v ->
                        v.toIntOrNull()?.let { n ->
                            onItemChange(item.copy(cantidad = n.coerceAtLeast(1)))
                        }
                    },
                    label = { Text("Cant.", fontSize = 11.sp) },
                    modifier = Modifier.width(90.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SmartColors.AzulPrincipal,
                        unfocusedBorderColor = SmartColors.BordeLinea
                    )
                )
            }

            // ── Advertencia stock ─────────────────────────────────────────
            if (libroSeleccionado != null && item.cantidad > libroSeleccionado.stockTotal) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = SmartColors.RojoTexto.copy(alpha = 0.08f)
                ) {
                    Text(
                        "⚠ Cantidad supera el stock disponible (${libroSeleccionado.stockTotal})",
                        fontSize = 11.sp,
                        color = SmartColors.RojoTexto,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }

            // ── Subtotal ──────────────────────────────────────────────────
            if (libroSeleccionado != null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Subtotal: ",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        formatter.format(subtotal),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = SmartColors.RojoTexto
                    )
                }
            }
        }
    }
}