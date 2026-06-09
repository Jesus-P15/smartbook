package co.edu.cecar.smartbooks.ui.screen.libros

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import co.edu.cecar.smartbooks.ui.screen.libros.componentsLibros.LibroFormulario
import co.edu.cecar.smartbooks.ui.screen.libros.componentsLibros.NuevoLibroViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NuevoLibroScreen(
    onLibroCreado: () -> Unit,
    navegarAtras: () -> Unit
) {
    val viewModel: NuevoLibroViewModel = viewModel()
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Nuevo Libro") },
                navigationIcon = {
                    IconButton(onClick = navegarAtras) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->

        LibroFormulario(
            form = state.form,
            onNombreChange = viewModel::onNombreChange,
            onNivelChange = viewModel::onNivelChange,
            onEdicionChange = viewModel::onEdicionChange,
            onTipoChange = viewModel::onTipoChange,
            onLoteChange = viewModel::onLoteChange,
            onStockChange = viewModel::onStockChange,
            onPrecioChange = viewModel::onPrecioChange,
            titulo = "Nuevo Libro",
            textoBoton = "Guardar libro",
            isLoading = state.isGuardando,
            paddingValues = padding,
            onGuardar = {
                viewModel.guardar {
                    scope.launch {
                        snackbarHostState.showSnackbar("✅ Libro creado exitosamente")

                    }
                    navegarAtras()
                }
            }
        )
    }
}