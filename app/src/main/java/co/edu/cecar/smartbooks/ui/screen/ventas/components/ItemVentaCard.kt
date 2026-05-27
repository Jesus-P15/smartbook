package co.edu.cecar.smartbooks.ui.screen.ventas.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.edu.cecar.smartbooks.data.Constants.SmartColors
import co.edu.cecar.smartbooks.viewmodel.ItemVentaUi

@Composable
fun ItemVentaCard(
    item: ItemVentaUi,
    index: Int,
    onItemChange: (ItemVentaUi) -> Unit,
    onEliminar: () -> Unit,
    mostrarEliminar: Boolean
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SmartColors.Superficie),
        border = CardDefaults.outlinedCardBorder()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
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

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = if (item.libroId == 0) "" else item.libroId.toString(),
                    onValueChange = { onItemChange(item.copy(libroId = it.toIntOrNull() ?: 0)) },
                    label = { Text("ID Libro", fontSize = 11.sp) },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SmartColors.AzulPrincipal,
                        unfocusedBorderColor = SmartColors.BordeLinea
                    )
                )
                OutlinedTextField(
                    value = if (item.lote == 0) "" else item.lote.toString(),
                    onValueChange = { onItemChange(item.copy(lote = it.toIntOrNull() ?: 0)) },
                    label = { Text("Lote", fontSize = 11.sp) },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SmartColors.AzulPrincipal,
                        unfocusedBorderColor = SmartColors.BordeLinea
                    )
                )
                OutlinedTextField(
                    value = item.cantidad.toString(),
                    onValueChange = { onItemChange(item.copy(cantidad = it.toIntOrNull() ?: 1)) },
                    label = { Text("Cant.", fontSize = 11.sp) },
                    modifier = Modifier.weight(0.8f),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SmartColors.AzulPrincipal,
                        unfocusedBorderColor = SmartColors.BordeLinea
                    )
                )
            }
        }
    }
}