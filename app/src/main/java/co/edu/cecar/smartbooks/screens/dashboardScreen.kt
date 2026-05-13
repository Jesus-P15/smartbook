package co.edu.cecar.smartbooks.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
@Composable
fun DashboardScreen(
    navegarAClientes: () -> Unit,
    navegarALibros: () -> Unit,
    navegarAVentas: () -> Unit,
    navegarALotes: () -> Unit,
    navegarAInventarios: () -> Unit,
    navegarAUsuarios: () -> Unit
) {

    Column {

        Text("Dashboard")

        Button(
            onClick = navegarAClientes
        ) {
            Text("Clientes")
        }

        Button(
            onClick = navegarALibros
        ) {
            Text("Libros")
        }

        Button(
            onClick = navegarAVentas
        ) {
            Text("Ventas")
        }

        Button(
            onClick = navegarALotes
        ) {
            Text("Lotes")
        }

        Button(
            onClick = navegarAInventarios
        ) {
            Text("Inventarios")
        }

        Button(
            onClick = navegarAUsuarios
        ) {
            Text("Usuarios")
        }
    }
}
