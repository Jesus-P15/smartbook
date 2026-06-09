package co.edu.cecar.smartbooks.ui.screen.libros.componentsLibros

data class NuevoLibroFormState(
    val nombre: String = "",
    val nivel: String = "",
    val edicion: String = "",
    val tipo: String = "Workbook",
    val lote: String = "",
    val stockInicial: String = "",
    val precioUnitario: String = "",


    val errorNombre: String? = null,
    val errorNivel: String? = null,
    val errorEdicion: String? = null,
    val errorTipo: String? = null,
    val errorLote: String? = null,
    val errorStock: String? = null,

    val isLoading: Boolean = false,
    val guardadoExitoso: Boolean = false
)

val tiposDeLibro = listOf("Workbook", "Textbook")

fun NuevoLibroFormState.validar(): NuevoLibroFormState {
    return copy(
        errorNombre  = if (nombre.isBlank()) "El nombre es requerido" else null,
        errorNivel = if (nivel.isBlank())
            "Requerido"
        else
            null,

        errorEdicion = if (edicion.isBlank())
            "Requerido"
        else
            null,

        errorTipo    = if (tipo.isBlank()) "Selecciona un tipo" else null,
        errorLote    = when {
            lote.isBlank()           -> "Requerido"
            lote.toIntOrNull() == null -> "Solo números"
            else                     -> null
        },
        errorStock   = when {
            stockInicial.isBlank()           -> "Requerido"
            stockInicial.toIntOrNull() == null -> "Solo números"
            stockInicial.toInt() < 0         -> "No puede ser negativo"
            else                             -> null
        }
    )
}

fun NuevoLibroFormState.esValido(): Boolean =
    errorNombre == null && errorNivel == null && errorEdicion == null &&
            errorTipo == null && errorLote == null && errorStock == null