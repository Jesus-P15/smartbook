package co.edu.cecar.smartbooks.ui.screen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import co.edu.cecar.smartbooks.data.Constants.CargandoIndicador
import co.edu.cecar.smartbooks.data.Constants.ErrorCard
import co.edu.cecar.smartbooks.data.Constants.EstadoVacio
import co.edu.cecar.smartbooks.data.Constants.SeccionHeader
import co.edu.cecar.smartbooks.data.Constants.SmartColors
import co.edu.cecar.smartbooks.ui.screen.components.MainLayout
import co.edu.cecar.smartbooks.ui.screen.ventas.components.DetalleVentaSheet
import co.edu.cecar.smartbooks.ui.screen.ventas.components.FiltrosCard
import co.edu.cecar.smartbooks.ui.screen.ventas.components.NuevaVentaSheet
import co.edu.cecar.smartbooks.ui.screen.ventas.components.VentaCard
import co.edu.cecar.smartbooks.ui.theme.RojoInstitucional
import co.edu.cecar.smartbooks.viewmodel.VentasViewModel

@Composable
fun VentasScreen(
    navegarADashboard: () -> Unit,
    navegarAClientes: () -> Unit,
    navegarALibros: () -> Unit,
    navegarALotes: () -> Unit,
    navegarAInventarios: () -> Unit,
    navegarAUsuarios: () -> Unit,
    navegarACerrarSesion: () -> Unit,
    navegarAPerfil:() -> Unit

) {
    val viewModel: VentasViewModel = viewModel()
    val state by viewModel.state.collectAsState()

    val ventasFiltradas by remember(state) {
        derivedStateOf { viewModel.ventasFiltradas() }
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            viewModel.cargarVentas()
        }
    }

    MainLayout(
        titulo = "Ventas",
        selectedItem = "ventas",
        navegarADashboard = navegarADashboard,
        navegarAClientes = navegarAClientes,
        navegarALibros = navegarALibros,
        navegarAVentas = {},
        navegarALotes = navegarALotes,
        navegarAInventarios = navegarAInventarios,
        navegarAUsuarios = navegarAUsuarios,
        navegarACerrarSesion = navegarACerrarSesion,
        navegarAPerfil= navegarAPerfil

    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(SmartColors.Superficie)
                .padding(padding)
        ) {
            // ── Contenido principal ──────────────────────────────────────────
            androidx.compose.foundation.lazy.LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                // Encabezado + botón nueva venta
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                            Text(
                                "Terminal de Ventas",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                "Sistema punto de venta",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Button(
                            onClick = { viewModel.abrirNuevaVenta() },
                            colors = ButtonDefaults.buttonColors(containerColor = RojoInstitucional),
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 10.dp)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(4.dp))
                            Text("Nueva venta", fontSize = 13.sp, fontWeight = FontWeight.Medium)
                        }
                    }
                }

                // Filtros
                item {
                    FiltrosCard(
                        fechaDesde = state.fechaDesde,
                        fechaHasta = state.fechaHasta,
                        busqueda = state.busqueda,
                        onFechaDesdeChange = viewModel::onFechaDesdeChange,
                        onFechaHastaChange = viewModel::onFechaHastaChange,
                        onBusquedaChange = viewModel::onBusquedaChange,
                        onLimpiar = viewModel::limpiarFiltros
                    )
                }

                // Loading
                if (state.isLoading) {
                    item { CargandoIndicador(color = RojoInstitucional) }
                }

                // Error
                state.error?.let {
                    item { ErrorCard(it) }
                }

                // Encabezado historial
                if (!state.isLoading) {
                    item {
                        SeccionHeader(
                            titulo = "Historial de ventas",
                            contador = "${ventasFiltradas.size} registros"
                        )
                    }
                }

                // Lista vacía
                if (ventasFiltradas.isEmpty() && !state.isLoading) {
                    item { EstadoVacio("🧾", "No hay ventas registradas") }
                }

                // Cards de ventas
                items(ventasFiltradas) { venta ->
                    VentaCard(
                        venta = venta,
                        onClick = { viewModel.verDetalle(venta) }
                    )
                }

                item { Spacer(Modifier.height(80.dp)) }
            }

            // Sheet detalle
            if (state.mostrarDetalle && state.ventaSeleccionada != null) {
                DetalleVentaSheet(
                    venta = state.ventaSeleccionada!!,
                    cargando = state.cargandoDetalle,
                    onCerrar = { viewModel.cerrarDetalle() }
                )
            }

            // Sheet nueva venta
            if (state.mostrarNuevaVenta) {
                NuevaVentaSheet(
                    viewModel = viewModel,
                    onCerrar = { viewModel.cerrarNuevaVenta() }
                )
            }
        }
    }
}


