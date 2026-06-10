package co.edu.cecar.smartbooks.ui.screen.ventas.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.edu.cecar.smartbooks.data.Constants.HandleSheet
import co.edu.cecar.smartbooks.data.Constants.SmartColors
import co.edu.cecar.smartbooks.ui.theme.RojoInstitucional
import co.edu.cecar.smartbooks.viewmodel.VentasViewModel
import java.text.NumberFormat
import java.util.Locale

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

    // Cierra solo cuando la venta se acaba de crear (no al abrir)
    var yaEstabaExitoso by remember { mutableStateOf(state.ventaCreadaExito) }
    LaunchedEffect(state.ventaCreadaExito) {
        if (state.ventaCreadaExito && !yaEstabaExitoso) onCerrar()
        yaEstabaExitoso = state.ventaCreadaExito
    }

    // Validaciones locales para mostrar errores en tiempo real
    var intentoGuardar by remember { mutableStateOf(false) }
    val identificacionVacia = identificacion.isBlank()
    val itemsSinLibro = items.any { it.libroId == 0 }

    val formatter = NumberFormat.getCurrencyInstance(Locale("es", "CO"))
    val totalVenta = items.sumOf { item ->
        val precio = state.libros.find { it.id == item.libroId }?.valorVentaPublico?.toDouble() ?: 0.0
        precio * item.cantidad
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
                .fillMaxHeight(0.95f)
                .clickable(enabled = false) {},
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {

                // ── Contenido scrollable ──────────────────────────────────
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(20.dp),
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

                    // ── Datos del cliente ─────────────────────────────────
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
                        isError = intentoGuardar && identificacionVacia,
                        supportingText = {
                            if (intentoGuardar && identificacionVacia) {
                                Text(
                                    "Este campo es obligatorio",
                                    color = MaterialTheme.colorScheme.error,
                                    fontSize = 11.sp
                                )
                            }
                        },
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

                    // ── Ítems ─────────────────────────────────────────────
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

                    // Advertencia ítems sin libro al intentar guardar
                    if (intentoGuardar && itemsSinLibro) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.errorContainer
                        ) {
                            Text(
                                "⚠ Selecciona un libro en cada ítem antes de guardar",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onErrorContainer,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                            )
                        }
                    }

                    // Spinner cargando catálogo
                    if (state.libros.isEmpty()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                strokeWidth = 2.dp,
                                color = SmartColors.AzulPrincipal
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                "Cargando catálogo…",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    items.forEachIndexed { index, item ->
                        ItemVentaCard(
                            item = item,
                            index = index,
                            libros = state.libros,
                            onItemChange = { viewModel.onItemChange(index, it) },
                            onEliminar = { viewModel.eliminarItem(index) },
                            mostrarEliminar = items.size > 1
                        )
                    }

                    // Error del API
                    state.errorCrear?.let {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.errorContainer
                        ) {
                            Text(
                                it,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onErrorContainer,
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                    }

                    Spacer(Modifier.height(8.dp))
                }

                // ── Total + botón fijo abajo ──────────────────────────────
                Column {
                    if (totalVenta > 0) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(SmartColors.Superficie)
                                .padding(horizontal = 20.dp, vertical = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Total a pagar",
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                formatter.format(totalVenta),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = RojoInstitucional
                            )
                        }
                    }

                    HorizontalDivider(color = SmartColors.BordeLinea)

                    Button(
                        onClick = {
                            intentoGuardar = true
                            // Solo llama al VM si pasa validaciones locales
                            if (!identificacionVacia && !itemsSinLibro) {
                                viewModel.crearVenta()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 14.dp)
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
                }
            }
        }
    }
}