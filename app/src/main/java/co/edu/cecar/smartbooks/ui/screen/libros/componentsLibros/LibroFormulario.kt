package co.edu.cecar.smartbooks.ui.screen.libros.componentsLibros

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import co.edu.cecar.smartbooks.data.Constants.LibroTipo

@Composable
fun LibroFormulario(
    form: NuevoLibroFormState,
    onNombreChange: (String) -> Unit,
    onNivelChange: (String) -> Unit,
    onEdicionChange: (String) -> Unit,
    onTipoChange: (String) -> Unit,
    onLoteChange: (String) -> Unit,
    onStockChange: (String) -> Unit,
    onPrecioChange: (String) -> Unit,
    titulo: String,
    textoBoton: String,
    isLoading: Boolean,
    onGuardar: () -> Unit,
    paddingValues: PaddingValues = PaddingValues(0.dp),
    contenidoExtra: @Composable (() -> Unit)? = null,
    mostrarInventario: Boolean = true
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(paddingValues)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = titulo,
            style = MaterialTheme.typography.headlineSmall
        )

        // Contenido extra (chips, errores, botón eliminar en editar)
        contenidoExtra?.invoke()

         SeccionFormulario(titulo = "Información básica") {
            CampoTexto(
                label = "Nombre",
                value = form.nombre,
                onValueChange = onNombreChange,
                errorMessage = form.errorNombre
            )
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                CampoTexto(
                    label = "Nivel",
                    value = form.nivel,
                    onValueChange = onNivelChange,
                    keyboardType = KeyboardType.Number,
                    modifier = Modifier.weight(1f),
                    errorMessage = form.errorNivel
                )
                CampoTexto(
                    label = "Edición",
                    value = form.edicion,
                    onValueChange = onEdicionChange,
                    keyboardType = KeyboardType.Number,
                    modifier = Modifier.weight(1f),
                    errorMessage = form.errorEdicion
                )
            }

            // Dropdown tipo — onTipoChange recibe el texto y el caller decide cómo convertirlo
            CampoDropdown(
                label = "Tipo *",
                opciones = listOf("Workbook", "StudentsBook", "ActivityBook"),
                seleccionado = LibroTipo.aTexto(form.tipo.toIntOrNull() ?: LibroTipo.WORKBOOK),
                onSeleccionar = { tipoTexto ->
                    onTipoChange(LibroTipo.desdeTexto(tipoTexto).toString())
                },
                errorMessage = form.errorTipo
            )
        }


        if (mostrarInventario) {
        // ── Sección inventario ────────────────────────────────────────────────
        SeccionFormulario(titulo = "Inventario") {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                CampoTexto(
                    label = "Lote",
                    value = form.lote,
                    onValueChange = onLoteChange,
                    keyboardType = KeyboardType.Number,
                    modifier = Modifier.weight(1f),
                    errorMessage = form.errorLote
                )
                CampoTexto(
                    label = "Stock",
                    value = form.stockInicial,
                    onValueChange = onStockChange,
                    keyboardType = KeyboardType.Number,
                    modifier = Modifier.weight(1f),
                    errorMessage = form.errorStock
                )
            }
            CampoTexto(
                label = "Precio venta",
                value = form.precioUnitario,
                onValueChange = onPrecioChange,
                keyboardType = KeyboardType.Decimal
            )
        }
        }

        // ── Botón guardar ─────────────────────────────────────────────────────
        Button(
            onClick = onGuardar,
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            Text(if (isLoading) "Guardando..." else textoBoton)
        }
    }
}