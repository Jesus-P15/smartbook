package co.edu.cecar.smartbooks.ui.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import co.edu.cecar.smartbooks.ui.screen.components.MainLayout
import co.edu.cecar.smartbooks.ui.screen.inventarios.components.FiltroLoteCard
import co.edu.cecar.smartbooks.ui.screen.inventarios.components.InventarioCard
import co.edu.cecar.smartbooks.ui.screen.inventarios.components.ResumenCard
import co.edu.cecar.smartbooks.ui.theme.RojoInstitucional
import co.edu.cecar.smartbooks.viewmodel.InventariosViewModel

// ── Paleta de colores ──────────────────────────────────────────────────────────
val AzulPrincipal   = Color(0xFF185FA5)
val AzulSuave       = Color(0xFFE6F1FB)
val AzulBorde       = Color(0xFFB5D4F4)
val AzulTexto       = Color(0xFF0C447C)

private val VerdeRelleno    = Color(0xFFEAF3DE)
val VerdeTexto      = Color(0xFF3B6D11)

private val AmbarRelleno    = Color(0xFFFAEEDA)
val AmbarTexto      = Color(0xFF854F0B)

private val RojoRelleno     = Color(0xFFFCEBEB)
val RojoTexto       = Color(0xFFA32D2D)

private val CoralSuave      = Color(0xFFFAECE7)
private val CoralTexto      = Color(0xFF993C1D)

private val Superficie      = Color(0xFFF8F8F8)
val BordeLinea      = Color(0xFFE8E8E8)

// ── Screen principal ───────────────────────────────────────────────────────────
@Composable
fun InventariosScreen(
    navegarADashboard: () -> Unit,
    navegarAClientes: () -> Unit,
    navegarALibros: () -> Unit,
    navegarAVentas: () -> Unit,
    navegarALotes: () -> Unit,
    navegarAUsuarios: () -> Unit,
    navegarACerrarSesion: () -> Unit,
    navegarAPerfil:() -> Unit

) {
    val viewModel: InventariosViewModel = viewModel()
    val state by viewModel.state.collectAsState()

    val inventariosFiltrados by remember(state) {
        derivedStateOf { viewModel.inventariosFiltrados() }
    }

    MainLayout(
        titulo = "Inventario",
        selectedItem = "inventarios",
        navegarADashboard = navegarADashboard,
        navegarAClientes = navegarAClientes,
        navegarALibros = navegarALibros,
        navegarAVentas = navegarAVentas,
        navegarALotes = navegarALotes,
        navegarAInventarios = {},
        navegarAUsuarios = navegarAUsuarios,
        navegarACerrarSesion = navegarACerrarSesion,
        navegarAPerfil = navegarAPerfil

    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Superficie),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            // ── Resumen ────────────────────────────────────────────────────
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ResumenCard(
                        titulo = "Total libros",
                        valor = state.totalLibros.toString(),
                        colorIcono = RojoInstitucional,
                        colorFondo = CoralSuave,
                        modifier = Modifier.weight(1f)
                    )
                    ResumenCard(
                        titulo = "Bajo stock",
                        valor = state.bajoStock.toString(),
                        colorIcono = AmbarTexto,
                        colorFondo = AmbarRelleno,
                        modifier = Modifier.weight(1f)
                    )
                    ResumenCard(
                        titulo = "Stock total",
                        valor = state.stockTotal.toString(),
                        colorIcono = AzulPrincipal,
                        colorFondo = AzulSuave,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // ── Filtro por lote ────────────────────────────────────────────
            item {
                FiltroLoteCard(
                    loteFiltro = state.loteFiltro,
                    loteSeleccionado = state.loteSeleccionado,
                    lotesSugeridos = viewModel.lotesSugeridos,
                    onFiltroChange = viewModel::onLoteFiltroChange,
                    onSeleccionar = viewModel::seleccionarLote,
                    onLimpiar = viewModel::limpiarFiltro
                )
            }

            // ── Loading ────────────────────────────────────────────────────
            if (state.isLoading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = RojoInstitucional, strokeWidth = 2.dp)
                    }
                }
            }

            // ── Error ──────────────────────────────────────────────────────
            state.error?.let { errorMsg ->
                item {
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = RojoRelleno),
                        border = CardDefaults.outlinedCardBorder().copy(
                            brush = androidx.compose.ui.graphics.SolidColor(RojoTexto.copy(alpha = 0.3f))
                        )
                    ) {
                        Text(
                            text = errorMsg,
                            modifier = Modifier.padding(14.dp),
                            fontSize = 13.sp,
                            color = RojoTexto
                        )
                    }
                }
            }

            // ── Encabezado sección ─────────────────────────────────────────
            if (!state.isLoading) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (state.loteSeleccionado != null)
                                "Lote ${state.loteSeleccionado}"
                            else "Todos los lotes",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "${inventariosFiltrados.size} libros",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // ── Lista vacía ────────────────────────────────────────────────
            if (inventariosFiltrados.isEmpty() && !state.isLoading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text("📦", fontSize = 36.sp)
                            Text(
                                "No hay inventario para este lote",
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            // ── Cards de inventario ────────────────────────────────────────
            items(inventariosFiltrados) { inv ->
                InventarioCard(inv)
            }

            item { Spacer(Modifier.height(8.dp)) }
        }
    }
}

// ── Badge de estado de stock ───────────────────────────────────────────────────
@Composable
fun StockBadge(stock: Int) {
    val (texto, colorFondo, colorTexto) = when {
        stock == 0  -> Triple("Sin stock",   RojoRelleno,   RojoTexto)
        stock <= 5  -> Triple("Bajo stock",  AmbarRelleno,  AmbarTexto)
        else        -> Triple("Stock OK",    VerdeRelleno,  VerdeTexto)
    }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(colorFondo)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = texto,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = colorTexto
        )
    }
}

// ── Color del stock disponible ─────────────────────────────────────────────────
fun stockColor(stock: Int): Color = when {
    stock == 0  -> RojoTexto
    stock <= 5  -> AmbarTexto
    else        -> VerdeTexto
}