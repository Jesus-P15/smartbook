package co.edu.cecar.smartbooks.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import co.edu.cecar.smartbooks.ui.screen.components.MainLayout
import co.edu.cecar.smartbooks.ui.screen.libros.componentsLibros.LibroCard
import co.edu.cecar.smartbooks.ui.screen.libros.componentsLibros.LibrosViewModel
import co.edu.cecar.smartbooks.ui.theme.RojoInstitucional


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibrosScreen(
    navegarADashboard: () -> Unit,
    navegarAClientes: () -> Unit,
    navegarAVentas: () -> Unit,
    navegarALotes: () -> Unit,
    navegarAInventarios: () -> Unit,
    navegarAUsuarios: () -> Unit,
    navegarACerrarSesion: () -> Unit,
    navegarANuevoLibro: () -> Unit,
    navegarAEditarLibro: (LibroId: Int) -> Unit,
    libroCreado: Boolean = false,
) {
    val viewModel = remember { LibrosViewModel() }
    val libros by viewModel.libros.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(libroCreado) {
        if (libroCreado) {
            snackbarHostState.showSnackbar("✅ Libro creado exitosamente")
        }
    }

    var query by remember { mutableStateOf("") }



    val librosFiltrados = remember(query, libros) {
        if (query.isBlank()) libros
        else libros.filter {
            it.nombre.contains(query, ignoreCase = true) ||
                    it.nivel.toString().contains(query)
        }
    }

    MainLayout(
        titulo = "Gestión de Libros",
        selectedItem = "libros",
        navegarADashboard = navegarADashboard,
        navegarAClientes = navegarAClientes,
        navegarALibros = {},
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
                                text = "Buscar por nombre, nivel...",
                                style = MaterialTheme.typography.bodySmall
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                            focusedBorderColor = RojoInstitucional
                        )
                    )

                    Button(
                        onClick = navegarANuevoLibro,
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

            // ── Contador de resultados ─────────────────────────────────────
            item {
                Text(
                    text = "${librosFiltrados.size} libro(s) encontrado(s)",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // ── Lista de LibroCards ────────────────────────────────────────
            if (librosFiltrados.isEmpty()) {
                item {
                    EmptyLibrosState(tieneQuery = query.isNotBlank())
                }
            } else {
                items(
                    items = librosFiltrados,
                    key = { it.id }
                ) { libro ->
                    LibroCard(
                        libro = libro,
                        onEditar = { navegarAEditarLibro(it.id) }
                    )
                }
            }
        }
    }
}




@Composable
private fun EmptyLibrosState(tieneQuery: Boolean) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = if (tieneQuery) "Sin resultados" else "No hay libros registrados",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = if (tieneQuery)
                    "Intenta con otro nombre o nivel"
                else
                    "Toca \"Nuevo\" para agregar el primer libro",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
            )
        }
    }
}