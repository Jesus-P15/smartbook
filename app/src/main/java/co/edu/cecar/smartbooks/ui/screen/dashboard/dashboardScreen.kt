package co.edu.cecar.smartbooks.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import co.edu.cecar.smartbooks.data.network.SessionManager
import co.edu.cecar.smartbooks.ui.screen.components.MainLayout
import co.edu.cecar.smartbooks.ui.screen.components.VentaHoyCard
import co.edu.cecar.smartbooks.ui.theme.AzulOscuro
import co.edu.cecar.smartbooks.ui.theme.RojoInstitucional
import co.edu.cecar.smartbooks.viewmodel.DashboardViewModel
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navegarAClientes: () -> Unit,
    navegarALibros: () -> Unit,
    navegarAVentas: () -> Unit,
    navegarALotes: () -> Unit,
    navegarAInventarios: () -> Unit,
    navegarAUsuarios: () -> Unit,
    navegarACerrarSesion: () -> Unit,
    navegarAPerfil: () -> Unit
) {
    val viewModel: DashboardViewModel = viewModel()
    val dashboard by viewModel.dashboard.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    // ── Recarga cada vez que la pantalla vuelve al foco ───────────────────
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            viewModel.cargarDashboard()
        }
    }

    val nombreUsuario = remember {
        SessionManager.obtenerPerfil()?.nombres?.split(" ")?.firstOrNull() ?: "Usuario"
    }

    MainLayout(
        titulo = "Dashboard",
        selectedItem = "dashboard",
        navegarADashboard = {},
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
                .padding(padding),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.linearGradient(
                                colors = listOf(AzulOscuro, RojoInstitucional)
                            )
                        )
                        .padding(horizontal = 20.dp, vertical = 24.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = "Hola, $nombreUsuario 👋",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Aquí está el resumen del negocio",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Resumen del mes",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    if (isLoading) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = RojoInstitucional)
                        }
                    } else {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            MetricaCard(
                                modifier = Modifier.weight(1f),
                                icono = Icons.Outlined.People,
                                label = "Clientes",
                                valor = dashboard?.totalClientes?.toString() ?: "—",
                                color = AzulOscuro
                            )
                            MetricaCard(
                                modifier = Modifier.weight(1f),
                                icono = Icons.Outlined.MenuBook,
                                label = "Libros",
                                valor = dashboard?.totalLibros?.toString() ?: "—",
                                color = Color(0xFF6B48FF)
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            MetricaCard(
                                modifier = Modifier.weight(1f),
                                icono = Icons.Outlined.ShoppingCart,
                                label = "Ventas mes",
                                valor = dashboard?.cantVentasMes?.toString() ?: "—",
                                color = Color(0xFF0F9D58)
                            )
                            MetricaCard(
                                modifier = Modifier.weight(1f),
                                icono = Icons.Outlined.AttachMoney,
                                label = "Ingresos mes",
                                valor = dashboard?.totalVentasMes?.let {
                                    NumberFormat.getCurrencyInstance(Locale("es", "CO")).format(it)
                                } ?: "—",
                                color = RojoInstitucional,
                                valorPequeno = true
                            )
                        }
                    }
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Acciones rápidas",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        AccionRapidaBtn(
                            modifier = Modifier.weight(1f),
                            icono = Icons.Default.AddShoppingCart,
                            label = "Nueva venta",
                            onClick = navegarAVentas
                        )
                        AccionRapidaBtn(
                            modifier = Modifier.weight(1f),
                            icono = Icons.Default.PersonAdd,
                            label = "Nuevo cliente",
                            onClick = navegarAClientes
                        )
                        AccionRapidaBtn(
                            modifier = Modifier.weight(1f),
                            icono = Icons.Default.LibraryAdd,
                            label = "Nuevo libro",
                            onClick = navegarALibros
                        )
                    }
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 24.dp, bottom = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Ventas de hoy",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        if (!dashboard?.ventasHoy.isNullOrEmpty()) {
                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = RojoInstitucional.copy(alpha = 0.12f)
                            ) {
                                Text(
                                    text = "${dashboard!!.ventasHoy.size} venta(s)",
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = RojoInstitucional,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }

            val ventas = dashboard?.ventasHoy
            if (ventas.isNullOrEmpty()) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                Icons.Outlined.ReceiptLong,
                                contentDescription = null,
                                modifier = Modifier.size(40.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
                            )
                            Text(
                                text = "Sin ventas hoy",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "Las ventas registradas hoy aparecerán aquí",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                            )
                        }
                    }
                }
            } else {
                items(ventas) { venta ->
                    VentaHoyCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        numeroRecibo = venta.numeroRecibo ?: "—",
                        cliente = venta.clienteNombre ?: "Cliente",
                        total = venta.total ?: 0.0,
                        fecha = venta.fecha ?: ""
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

@Composable
private fun MetricaCard(
    modifier: Modifier = Modifier,
    icono: ImageVector,
    label: String,
    valor: String,
    color: Color,
    valorPequeno: Boolean = false
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(color.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icono,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(20.dp)
                )
            }
            Text(
                text = valor,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                fontSize = if (valorPequeno) 18.sp else 24.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun AccionRapidaBtn(
    modifier: Modifier = Modifier,
    icono: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = RojoInstitucional.copy(alpha = 0.07f)
        ),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = icono,
                contentDescription = label,
                tint = RojoInstitucional,
                modifier = Modifier.size(22.dp)
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Medium,
                color = RojoInstitucional,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

