package co.edu.cecar.smartbooks.ui.screen.clientes


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import co.edu.cecar.smartbooks.ui.theme.RojoInstitucional
import co.edu.cecar.smartbooks.viewmodel.EditarClienteViewModel
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarClienteScreen(
    identificacion: String,
    navegarAtras: () -> Unit
) {
    val viewModel: EditarClienteViewModel = viewModel()
    val state by viewModel.state.collectAsState()
    val form = state.form

    var mostrarDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    LaunchedEffect(identificacion) {
        viewModel.cargarCliente(identificacion)
    }

    // ── DatePicker ─────────────────────────────────────────────────────────────
    if (mostrarDatePicker) {
        DatePickerDialog(
            onDismissRequest = { mostrarDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val fecha = Instant.ofEpochMilli(millis)
                            .atZone(ZoneId.of("UTC"))
                            .toLocalDate()
                            .format(DateTimeFormatter.ISO_LOCAL_DATE)
                        viewModel.onFechaChange(fecha)
                    }
                    mostrarDatePicker = false
                }) { Text("Aceptar") }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDatePicker = false }) { Text("Cancelar") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar Cliente") },
                navigationIcon = {
                    IconButton(onClick = navegarAtras) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->

        // ── Loading ────────────────────────────────────────────────────────────
        if (state.isCargando) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = RojoInstitucional)
            }
            return@Scaffold
        }

        // ── Formulario ─────────────────────────────────────────────────────────
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text("Editar Cliente", style = MaterialTheme.typography.headlineSmall)

            // Chip con identificación (solo lectura)
            SuggestionChip(
                onClick = {},
                label = { Text("ID: ${state.identificacion}") }
            )

            // ── Nombres ───────────────────────────────────────────────────
            OutlinedTextField(
                value = form.nombres,
                onValueChange = viewModel::onNombresChange,
                label = { Text("Nombres completos") },
                isError = form.errorNombres != null,
                supportingText = form.errorNombres?.let { { Text(it) } },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // ── Email ─────────────────────────────────────────────────────
            OutlinedTextField(
                value = form.email,
                onValueChange = viewModel::onEmailChange,
                label = { Text("Email") },
                isError = form.errorEmail != null,
                supportingText = form.errorEmail?.let { { Text(it) } },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // ── Celular ───────────────────────────────────────────────────
            OutlinedTextField(
                value = form.celular,
                onValueChange = viewModel::onCelularChange,
                label = { Text("Celular") },
                isError = form.errorCelular != null,
                supportingText = form.errorCelular?.let { { Text(it) } },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // ── Fecha de nacimiento (DatePicker interactivo) ───────────────
            OutlinedTextField(
                value = form.fechaNacimiento,
                onValueChange = {},
                label = { Text("Fecha de nacimiento") },
                isError = form.errorFecha != null,
                supportingText = form.errorFecha?.let { { Text(it) } },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { mostrarDatePicker = true }) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Seleccionar fecha",
                            tint = RojoInstitucional
                        )
                    }
                }
            )

            // ── Error general ─────────────────────────────────────────────
            state.errorGeneral?.let {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Text(text = it, modifier = Modifier.padding(12.dp))
                }
            }

            // ── Botón guardar ─────────────────────────────────────────────
            Button(
                onClick = { viewModel.guardar { navegarAtras() } },
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isGuardando,
                colors = ButtonDefaults.buttonColors(containerColor = RojoInstitucional)
            ) {
                Text(if (state.isGuardando) "Guardando..." else "Guardar cambios")
            }
        }
    }
}