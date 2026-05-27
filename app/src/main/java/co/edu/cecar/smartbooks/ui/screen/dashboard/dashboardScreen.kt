package co.edu.cecar.smartbooks.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import co.edu.cecar.smartbooks.data.network.SessionManager
import co.edu.cecar.smartbooks.ui.screen.components.DashboardCard
import co.edu.cecar.smartbooks.ui.screen.components.DashboardCardItem
import co.edu.cecar.smartbooks.ui.screen.components.MainLayout
import co.edu.cecar.smartbooks.viewmodel.DashboardViewModel

import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navegarAClientes: () -> Unit,
    navegarALibros: () -> Unit,
    navegarAVentas: () -> Unit,
    navegarALotes: () -> Unit,
    navegarAInventarios: () -> Unit,
    navegarAUsuarios: () -> Unit,
    navegarACerrarSesion: () -> Unit
) {


    val context = LocalContext.current

    val viewModel: DashboardViewModel = viewModel(

    )

    val dashboard by viewModel.dashboard.collectAsState()

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
        navegarACerrarSesion = navegarACerrarSesion
    ) { padding ->

        val cards = listOf(
            DashboardCardItem(
                icono = Icons.Default.Person,
                titulo = "Total Clientes",
                valor = dashboard?.totalClientes?.toString() ?: "0"

            ),
            DashboardCardItem(
                titulo = "Libros Registrados",
                valor = dashboard?.totalLibros?.toString() ?: "0",
                icono = Icons.Default.Book
            ),
            DashboardCardItem(
                titulo = "Ventas Mes",
                valor = dashboard?.cantVentasMes?.toString() ?: "0",
                icono = Icons.Default.ShoppingCart
            ),
            DashboardCardItem(
                titulo = "Ingresos Mes",
                valor = "$${dashboard?.totalVentasMes ?: 0}",
                icono = Icons.Default.AttachMoney
            )
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {

                item {
                Text(
                    text = "Resumen General",
                    style = MaterialTheme.typography.headlineSmall
                )
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        DashboardCard(
                            item = cards[0],
                            modifier = Modifier.weight(1f)
                        )
                        DashboardCard(
                            item = cards[1],
                            modifier = Modifier.weight(1f)
                        )
                    }
                  Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        DashboardCard(
                            item = cards[2],
                            modifier = Modifier.weight(1f)
                        )
                        DashboardCard(
                            item = cards[3],
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }


            item {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Últimas ventas de hoy",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "No hay ventas registradas hoy",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }


            item {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Acciones rápidas",
                            style = MaterialTheme.typography.titleMedium
                        )


                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = navegarAClientes,
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Agregar cliente")
                            }
                            Button(
                                onClick = navegarALibros,
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Registrar libro")
                            }
                        }

                        Button(
                            onClick = navegarAVentas,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Nueva venta")
                        }
                    }
                }
            }


            item {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Información general",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "Bienvenido al panel administrativo de SmartBooks.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}