package co.edu.cecar.smartbooks.ui.screen.ventas.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.edu.cecar.smartbooks.data.Constants.ErrorCard
import co.edu.cecar.smartbooks.data.Constants.HandleSheet
import co.edu.cecar.smartbooks.data.Constants.SmartColors
import co.edu.cecar.smartbooks.ui.theme.RojoInstitucional
import co.edu.cecar.smartbooks.viewmodel.VentasViewModel


@Composable
fun NuevaVentaSheet(
    viewModel: VentasViewModel,
    onCerrar: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val identificacion by viewModel.identificacionCliente.collectAsState()
    val comprobante by viewModel.numeroComprobante.collectAsState()
    val observaciones by viewModel.observaciones.collectAsState()
    val items by viewModel.items.collectAsState()

    LaunchedEffect(state.ventaCreadaExito) {
        if (state.ventaCreadaExito) onCerrar()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.4f))
            .clickable { onCerrar() },
        contentAlignment = Alignment.BottomCenter
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.92f)
                .clickable(enabled = false) {},
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                HandleSheet(modifier = Modifier.align(Alignment.CenterHorizontally))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Nueva Venta", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    IconButton(onClick = onCerrar) {
                        Icon(Icons.Default.Close, contentDescription = "Cerrar")
                    }
                }

                HorizontalDivider(color = SmartColors.BordeLinea)

                // ── Datos del cliente ──────────────────────────────────────
                Text(
                    "DATOS DEL CLIENTE",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                OutlinedTextField(
                    value = identificacion,
                    onValueChange = viewModel::onIdentificacionChange,
                    label = { Text("Identificación del cliente *") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SmartColors.AzulPrincipal,
                        unfocusedBorderColor = SmartColors.BordeLinea
                    )
                )

                OutlinedTextField(
                    value = comprobante,
                    onValueChange = viewModel::onComprobanteChange,
                    label = { Text("Número de comprobante") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SmartColors.AzulPrincipal,
                        unfocusedBorderColor = SmartColors.BordeLinea
                    )
                )

                OutlinedTextField(
                    value = observaciones,
                    onValueChange = viewModel::onObservacionesChange,
                    label = { Text("Observaciones") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3,
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SmartColors.AzulPrincipal,
                        unfocusedBorderColor = SmartColors.BordeLinea
                    )
                )

                HorizontalDivider(color = SmartColors.BordeLinea)

                // ── Ítems ──────────────────────────────────────────────────
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "ÍTEMS",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    TextButton(onClick = { viewModel.agregarItem() }) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("Agregar ítem", fontSize = 12.sp)
                    }
                }

                items.forEachIndexed { index, item ->
                    ItemVentaCard(
                        item = item,
                        index = index,
                        onItemChange = { viewModel.onItemChange(index, it) },
                        onEliminar = { viewModel.eliminarItem(index) },
                        mostrarEliminar = items.size > 1
                    )
                }

                // Error
                state.errorCrear?.let { ErrorCard(it) }

                // Botón guardar
                Button(
                    onClick = { viewModel.crearVenta() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    enabled = !state.creandoVenta,
                    colors = ButtonDefaults.buttonColors(containerColor = RojoInstitucional),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    if (state.creandoVenta) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text("Guardar venta", fontSize = 15.sp, fontWeight = FontWeight.Medium)
                    }
                }

                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

