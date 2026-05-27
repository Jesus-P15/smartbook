package co.edu.cecar.smartbooks.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import co.edu.cecar.smartbooks.ui.screen.clientes.components.ClienteCard
import co.edu.cecar.smartbooks.ui.screen.components.MainLayout
import co.edu.cecar.smartbooks.ui.theme.RojoInstitucional
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import co.edu.cecar.smartbooks.viewmodel.ClientesViewModel

@OptIn(ExperimentalMaterial3Api::class)

        @Composable
        fun ClientesScreen(
            navegarADashboard: () -> Unit,
            navegarALibros: () -> Unit,
            navegarAVentas: () -> Unit,
            navegarALotes: () -> Unit,
            navegarAInventarios: () -> Unit,
            navegarAUsuarios: () -> Unit,
            navegarACerrarSesion: () -> Unit,
            navegarANuevoCliente: () -> Unit = {},
            navegarAEditarCliente: (identificacion: String) -> Unit = {},
            ) {


            val viewModel: ClientesViewModel = viewModel()
            val state by viewModel.state.collectAsState()
            var query by remember { mutableStateOf("") }



            val clientesFiltrados = remember(query, state.clientes) {
                if (query.isBlank()) state.clientes
                else state.clientes.filter {
                    it.nombres.contains(query, ignoreCase = true) ||
                            it.identificacion.contains(query)
                }
            }

            LaunchedEffect(Unit) {
                viewModel.cargarClientes()
             }
            val lifecycleOwner = LocalLifecycleOwner.current
            LaunchedEffect(lifecycleOwner) {
                lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                    viewModel.cargarClientes()
                }
            }


    MainLayout(
                titulo = "Gestión de Clientes",
                selectedItem = "clientes",
                navegarADashboard = navegarADashboard,
                navegarAClientes = {},
                navegarALibros = navegarALibros,
                navegarAVentas = navegarAVentas,
                navegarALotes = navegarALotes,
                navegarAInventarios = navegarAInventarios,
                navegarAUsuarios = navegarAUsuarios,
                navegarACerrarSesion = navegarACerrarSesion
            ) { padding ->

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {

                    // ── Barra de búsqueda + botón Nuevo ───────────────────────────
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
                                        text = "Buscar por nombre o identificación...",
                                        style = MaterialTheme.typography.bodySmall
                                    ) },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Search,
                                        contentDescription = null,
                                        modifier = Modifier.size(18.dp)
                                    ) },
                                singleLine = true,
                                shape = RoundedCornerShape(10.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                                    focusedBorderColor = RojoInstitucional
                                )
                            )

                            Button(
                                onClick = navegarANuevoCliente,
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = RojoInstitucional,
                                    contentColor = Color.White
                                ),
                                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 12.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = "Nuevo", style = MaterialTheme.typography.labelLarge)
                            }
                        }
                    }

                            // ── Loading ───────────────────────────────────────────────────
                    if (state.isLoading) {
                        item {
                            Box(
                                modifier = Modifier.fillMaxWidth().padding(32.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(color = RojoInstitucional)
                            }
                        }
                    }

                            // ── Error ─────────────────────────────────────────────────────
                    state.error?.let { error ->
                        item {
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.errorContainer
                                )
                            ) {
                                Text(
                                    text = error,
                                    modifier = Modifier.padding(12.dp),
                                    color = MaterialTheme.colorScheme.onErrorContainer
                                )
                            }
                        }
                    }
                            // ── Contador ──────────────────────────────────────────────────
                    item {
                        Text(
                            text = "${clientesFiltrados.size} cliente(s) encontrado(s)",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                            // ── Lista de ClienteCards ──────────────────────────────────────
                    if (clientesFiltrados.isEmpty() && !state.isLoading) {
                        item {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                                )
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxWidth().padding(32.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = if (query.isNotBlank()) "Sin resultados" else "No hay clientes registrados",
                                        style = MaterialTheme.typography.titleSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        text = if (query.isNotBlank())
                                            "Intenta con otro nombre o identificación"
                                        else
                                            "Toca \"Nuevo\" para agregar el primer cliente",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                                    )
                                }
                            }
                        }
                    } else {
                        items(
                            items = clientesFiltrados,
                            key = { it.id }
                        ) { cliente ->
                            ClienteCard(
                                cliente = cliente,
                                onEditar = { navegarAEditarCliente(it.identificacion) }
                            )
                        }
                    }
                }
            }
        }
