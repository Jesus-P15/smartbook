package co.edu.cecar.smartbooks.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import co.edu.cecar.smartbooks.ui.screen.components.MainLayout

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

    navegarACerrarSesion: () -> Unit

) {

    MainLayout(

        titulo = "Lotes",

        selectedItem = "lotes",

        navegarADashboard = navegarADashboard,

        navegarAClientes = navegarAClientes,

        navegarALibros = navegarALibros,

        navegarAVentas = navegarAVentas,

        navegarALotes = {},

        navegarAInventarios = navegarAInventarios,

        navegarAUsuarios = navegarAUsuarios,

        navegarACerrarSesion = navegarACerrarSesion

    ) { padding ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)

        ) {

            Text(

                text = "Contenido lotes",

                style =
                    MaterialTheme.typography.headlineSmall
            )
        }
    }
}