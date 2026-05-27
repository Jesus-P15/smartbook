package co.edu.cecar.smartbooks.ui.screen.libros.componentsLibros

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.edu.cecar.smartbooks.data.Constants.LibroTipo
import co.edu.cecar.smartbooks.data.DataClass.libro.UpdateLibroRequest
import co.edu.cecar.smartbooks.data.repository.LibroRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class EditarLibroUiState(
    val form: NuevoLibroFormState = NuevoLibroFormState(),
    val libroId: Int = -1,
    val nombreOriginal: String = "",
    val stockActual: Int = 0,
    val isCargando: Boolean = true,
    val isGuardando: Boolean = false,
    val isEliminando: Boolean = false,
    val errorGeneral: String? = null,
    val mostrarConfirmarEliminar: Boolean = false

)

class EditarLibroViewModel : ViewModel() {

    private val repository = LibroRepository()

    private val _state = MutableStateFlow(EditarLibroUiState())
    val state: StateFlow<EditarLibroUiState> = _state



    fun cargarLibro(libroId: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isCargando = true) }

            repository.obtenerLibroPorId(libroId)
                .onSuccess { libro ->
                    _state.update {
                        it.copy(
                            libroId = libro.id,
                            nombreOriginal = libro.nombre,
                            stockActual = libro.stockTotal,
                            isCargando = false,
                            form = NuevoLibroFormState(
                                nombre = libro.nombre,
                                nivel = libro.nivel,
                                edicion = libro.edicion,
                                tipo = LibroTipo.aTexto(libro.tipo.toInt()),
                                lote = libro.lote.toString(),
                                stockInicial = libro.stockTotal.toString(),
                                precioUnitario = libro.valorVentaPublico.toString()
                            )
                        )
                    }
                }
                .onFailure { error ->
                    println("ERROR CARGAR LIBRO: ${error.message}")
                    _state.update {
                        it.copy(
                            isCargando = false,
                            errorGeneral = error.message ?: "Error al cargar libro"
                        )
                    }
                }
        }
    }

    // CHANGES
    fun onNombreChange(v: String) = _state.update { it.copy(form = it.form.copy(nombre = v, errorNombre = null)) }
    fun onNivelChange(v: String) = _state.update { it.copy(form = it.form.copy(nivel = v, errorNivel = null)) }
    fun onEdicionChange(v: String) = _state.update { it.copy(form = it.form.copy(edicion = v, errorEdicion = null)) }
    fun onTipoChange(v: String) = _state.update { it.copy(form = it.form.copy(tipo = v, errorTipo = null)) }
    fun onLoteChange(v: String) = _state.update { it.copy(form = it.form.copy(lote = v, errorLote = null)) }
    fun onStockChange(v: String) = _state.update { it.copy(form = it.form.copy(stockInicial = v, errorStock = null)) }
    fun onPrecioChange(v: String) = _state.update { it.copy(form = it.form.copy(precioUnitario = v)) }

    // GUARDAR
    fun guardar(onExito: () -> Unit) {
        val formValidado = _state.value.form.validar()
        _state.update { it.copy(form = formValidado) }
        if (!formValidado.esValido()) return

        viewModelScope.launch {
            _state.update { it.copy(isGuardando = true, errorGeneral = null) }

            val request = UpdateLibroRequest(
                nombre = formValidado.nombre,
                nivel = formValidado.nivel,
                tipo = LibroTipo.desdeTexto(formValidado.tipo),
                edicion = formValidado.edicion
            )

            repository.editarLibro(id = _state.value.libroId, request = request)      .onSuccess {
                    _state.update { it.copy(isGuardando = false) }
                    onExito()
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(
                            isGuardando = false,
                            errorGeneral = error.message ?: "Error al guardar"
                        )
                    }
                }
        }
    }
}