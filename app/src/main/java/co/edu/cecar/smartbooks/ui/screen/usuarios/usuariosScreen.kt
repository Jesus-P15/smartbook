package co.edu.cecar.smartbooks.ui.screen


import androidx.compose.foundation.background
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
import co.edu.cecar.smartbooks.data.Constants.SmartColors
import co.edu.cecar.smartbooks.ui.screen.components.MainLayout
import co.edu.cecar.smartbooks.ui.screen.usuarios.components.EditarUsuarioSheet
import co.edu.cecar.smartbooks.ui.screen.usuarios.components.NuevoUsuarioSheet
import co.edu.cecar.smartbooks.ui.screen.usuarios.components.UsuarioCard
import co.edu.cecar.smartbooks.ui.theme.RojoInstitucional
import co.edu.cecar.smartbooks.viewmodel.UsuariosViewModel

@Composable
fun UsuariosScreen(
    navegarADashboard: () -> Unit,
    navegarAClientes: () -> Unit,
    navegarALibros: () -> Unit,
    navegarALotes: () -> Unit,
    navegarAInventarios: () -> Unit,
    navegarAVentas: () -> Unit,
    navegarACerrarSesion: () -> Unit,
    navegarAPerfil:() -> Unit
) {

    val viewModel: UsuariosViewModel = viewModel()
    val state by viewModel.state.collectAsState()
    val usuariosFiltrados by remember(state) {
        derivedStateOf { viewModel.usuariosFiltrados() }
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            viewModel.cargarUsuarios()
        }
    }

    MainLayout(
        titulo = "Usuarios",
        selectedItem = "usuarios",
        navegarADashboard = navegarADashboard,
        navegarAClientes = navegarAClientes,
        navegarALibros = navegarALibros,
        navegarAVentas = navegarAVentas,
        navegarALotes = navegarALotes,
        navegarAInventarios = navegarAInventarios,
        navegarAUsuarios = {},
        navegarACerrarSesion = navegarACerrarSesion,
        navegarAPerfil = navegarAPerfil

    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(SmartColors.Superficie)
                .padding(padding)
        ) {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                // ── Encabezado ───────────────────────────────────────────────
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Text(
                                "Gestión de Usuarios",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                "Administra los usuarios del sistema SmartBook",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Spacer(Modifier.width(8.dp))
                        Button(
                            onClick = { viewModel.abrirNuevoUsuario() },
                            colors = ButtonDefaults.buttonColors(containerColor = RojoInstitucional),
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 10.dp)
                        ) {
                            Icon(Icons.Default.Add, null, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(4.dp))
                            Text("Nuevo", fontSize = 13.sp, fontWeight = FontWeight.Medium)
                        }
                    }
                }

                // ── Buscador ─────────────────────────────────────────────────
                item {
                    OutlinedTextField(
                        value = state.busqueda,
                        onValueChange = viewModel::onBusquedaChange,
                        placeholder = { Text("Buscar nombre, ID o email...", fontSize = 13.sp) },
                        leadingIcon = { Icon(Icons.Default.Search, null) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )
                }

                // ── Loading / Error ──────────────────────────────────────────
                if (state.isLoading) {
                    item { CargandoIndicador(color = RojoInstitucional) }
                }
                state.error?.let {
                    item { ErrorCard(it) }
                }

                // ── Contador + lista ─────────────────────────────────────────
                if (!state.isLoading) {
                    item {
                        Text(
                            "${usuariosFiltrados.size} usuario(s) encontrado(s)",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    if (usuariosFiltrados.isEmpty()) {
                        item { EstadoVacio("👤", "No hay usuarios registrados") }
                    }

                    items(usuariosFiltrados) { usuario ->
                        UsuarioCard(
                            usuario = usuario,
                            onEditar = { viewModel.abrirEditarUsuario(usuario) }
                        )
                    }
                }

                item { Spacer(Modifier.height(80.dp)) }
            }

            // ── Sheets ───────────────────────────────────────────────────────
            if (state.mostrarNuevoUsuario) {
                NuevoUsuarioSheet(
                    viewModel = viewModel,
                    onCerrar = { viewModel.cerrarNuevoUsuario() }
                )
            }
            if (state.mostrarEditarUsuario && state.usuarioAEditar != null) {
                EditarUsuarioSheet(
                    viewModel = viewModel,
                    onCerrar = { viewModel.cerrarEditarUsuario() }
                )
            }
        }
    }
}