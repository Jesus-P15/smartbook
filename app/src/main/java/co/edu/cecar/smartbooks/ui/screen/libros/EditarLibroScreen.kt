package co.edu.cecar.smartbooks.ui.screen.libros.componentsLibros


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import co.edu.cecar.smartbooks.ui.theme.RojoInstitucional

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarLibroScreen(

    libroId: Int,

    navegarAtras: () -> Unit

) {

    val viewModel:
            EditarLibroViewModel =
        viewModel()

    val state by
    viewModel.state.collectAsState()

    LaunchedEffect(libroId) {

        viewModel.cargarLibro(
            libroId
        )
    }



    Scaffold(

        topBar = {

            TopAppBar(

                title = {

                    Text("Editar Libro")
                },

                navigationIcon = {

                    IconButton(

                        onClick = navegarAtras

                    ) {

                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { padding ->



        if (state.isCargando) {

            Box(

                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),

                contentAlignment =
                    Alignment.Center

            ) {

                CircularProgressIndicator(
                    color = RojoInstitucional
                )
            }

            return@Scaffold
        }




        LibroFormulario(
            mostrarInventario = false,
            form = state.form,

            onNombreChange =
                viewModel::onNombreChange,

            onNivelChange =
                viewModel::onNivelChange,

            onEdicionChange =
                viewModel::onEdicionChange,

            onTipoChange =
                viewModel::onTipoChange,

            onLoteChange =
                viewModel::onLoteChange,

            onStockChange =
                viewModel::onStockChange,

            onPrecioChange =
                viewModel::onPrecioChange,

            titulo =
                "Editar Libro",

            textoBoton =
                "Guardar cambios",

            isLoading =
                state.isGuardando,

            onGuardar = {

                viewModel.guardar {

                    navegarAtras()
                }
            },

            contenidoExtra = {

                Column(

                    verticalArrangement =
                        Arrangement.spacedBy(8.dp)

                ) {

                    Row(

                        horizontalArrangement =
                            Arrangement.spacedBy(8.dp)

                    ) {

                        InfoChip(
                            texto =
                                "ID: ${state.libroId}"
                        )

                        StockChip(
                            stock =
                                state.stockActual
                        )
                    }

                    state.errorGeneral?.let {

                        Card(

                            colors =
                                CardDefaults.cardColors(

                                    containerColor =
                                        MaterialTheme.colorScheme.errorContainer
                                )
                        ) {

                            Text(

                                text = it,

                                modifier =
                                    Modifier.padding(12.dp)
                            )
                        }
                    }
                }
            }
        )
    }
}