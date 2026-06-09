package co.edu.cecar.smartbooks.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import co.edu.cecar.smartbooks.data.DataClass.Lotes.LoteResponse
import co.edu.cecar.smartbooks.ui.screen.components.MainLayout
import co.edu.cecar.smartbooks.ui.theme.RojoInstitucional
import co.edu.cecar.smartbooks.viewmodel.LotesViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LotesScreen(
    navegarADashboard: () -> Unit,
    navegarAClientes: () -> Unit,
    navegarALibros: () -> Unit,
    navegarAVentas: () -> Unit,
    navegarALotes: () -> Unit,
    navegarAInventarios: () -> Unit,
    navegarAUsuarios: () -> Unit,
    navegarACerrarSesion: () -> Unit,
    navegarAPerfil:() -> Unit

) {
    val viewModel: LotesViewModel = viewModel()
    val state by viewModel.state.collectAsState()

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    var query by remember { mutableStateOf("") }

    val lotesFiltrados = remember(query, state.lotes) {
        if (query.isBlank()) state.lotes
        else state.lotes.filter { it.codigo.toString().contains(query) }
    }

    // ── Sheet: Nuevo Lote ──────────────────────────────────────────────────
    if (state.mostrarDialogoNuevo) {
        ModalBottomSheet(
            onDismissRequest = viewModel::ocultarDialogo,
            sheetState = sheetState,
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
        ) {
            NuevoLoteSheet(
                valor = state.nuevoLoteTexto,
                isCreando = state.isCreando,
                errorCrear = state.errorCrear,
                onValorChange = viewModel::onNuevoLoteChange,
                onCrear = {
                    viewModel.crearLote(onExito = {
                        scope.launch { sheetState.hide() }
                            .invokeOnCompletion { viewModel.ocultarDialogo() }
                    })
                },
                onCancelar = {
                    scope.launch { sheetState.hide() }
                        .invokeOnCompletion { viewModel.ocultarDialogo() }
                }
            )
        }
    }

    MainLayout(
        titulo = "Gestión de Lotes",
        selectedItem = "lotes",
        navegarADashboard = navegarADashboard,
        navegarAClientes = navegarAClientes,
        navegarALibros = navegarALibros,
        navegarAVentas = navegarAVentas,
        navegarALotes = navegarALotes,
        navegarAInventarios = navegarAInventarios,
        navegarAUsuarios = navegarAUsuarios,
        navegarACerrarSesion = navegarACerrarSesion,
        navegarAPerfil = navegarAPerfil

    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {

            // ── Barra búsqueda + botón nuevo ───────────────────────────────
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = query,
                        onValueChange = { query = it },
                        modifier = Modifier.weight(1f),
                        placeholder = {
                            Text(
                                "Buscar por identificación o nombre...",
                                style = MaterialTheme.typography.bodySmall
                            )
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = RojoInstitucional,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
                        )
                    )
                    Button(
                        onClick = viewModel::mostrarDialogo,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = RojoInstitucional,
                            contentColor = Color.White
                        ),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 12.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Nuevo", style = MaterialTheme.typography.labelLarge)
                    }
                }
            }

            // ── Cargando ───────────────────────────────────────────────────
            if (state.isLoading) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = RojoInstitucional)
                    }
                }
                return@LazyColumn
            }

            // ── Error ──────────────────────────────────────────────────────
            state.errorCrear?.let { msg ->
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        )
                    ) {
                        Text(
                            text = "Error: $msg",
                            modifier = Modifier.padding(16.dp),
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                }
                return@LazyColumn
            }

            // ── Encabezado tabla ───────────────────────────────────────────
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(
                        topStart = 12.dp, topEnd = 12.dp,
                        bottomStart = 0.dp, bottomEnd = 0.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        Text(
                            text = "Lote",
                            modifier = Modifier.weight(1f),
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Text(
                            text = "Actual",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }

            // ── Filas ──────────────────────────────────────────────────────
            if (lotesFiltrados.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(0.dp)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth().padding(24.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "No hay lotes registrados",
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            } else {
                items(lotesFiltrados, key = { it.codigo }) { lote ->
                    LoteFila(lote = lote)
                }
            }
        }
    }
}

// ── Sheet contenido: Nuevo Lote ───────────────────────────────────────────────
@Composable
private fun NuevoLoteSheet(
    valor: String,
    isCreando: Boolean,
    errorCrear: String?,
    onValorChange: (String) -> Unit,
    onCrear: () -> Unit,
    onCancelar: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(top = 8.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Título
        Text(
            text = "Nuevo Lote",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = "Ingresa el número del nuevo lote a registrar.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        // Campo
        OutlinedTextField(
            value = valor,
            onValueChange = onValorChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Número de lote") },
            placeholder = { Text("Ej. 20261") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = RojoInstitucional
            )
        )

        // Botones
        Button(
            onClick = onCrear,
            modifier = Modifier.fillMaxWidth().height(48.dp),
            enabled = valor.toIntOrNull() != null && !isCreando,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = RojoInstitucional,
                contentColor = Color.White
            )
        ) {
            if (isCreando) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp
                )
            } else {
                Text("Crear lote", style = MaterialTheme.typography.labelLarge)
            }
        }

        OutlinedButton(
            onClick = onCancelar,
            modifier = Modifier.fillMaxWidth().height(44.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Cancelar")
        }
    }
}

// ── Fila individual ───────────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoteFila(lote: LoteResponse) {
    val esActual = lote.actual == true
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = lote.codigo.toString(),
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = if (esActual) Color(0xFFE1F5EE) else Color(0xFFFADAD6)
            ) {
                Text(
                    text = if (esActual) "Sí" else "No",
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Medium,
                    color = if (esActual) Color(0xFF0F6E56) else Color(0xFF993C1D)
                )
            }
        }
        HorizontalDivider(
            thickness = 0.5.dp,
            color = MaterialTheme.colorScheme.outlineVariant
        )
    }
}