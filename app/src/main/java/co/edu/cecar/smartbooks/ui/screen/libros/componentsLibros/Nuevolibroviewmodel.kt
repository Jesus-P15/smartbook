package co.edu.cecar.smartbooks.ui.screen.libros.componentsLibros

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.edu.cecar.smartbooks.data.DataClass.libro.CreateLibroRequest
import co.edu.cecar.smartbooks.data.repository.LibroRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class NuevoLibroUiState(
    val form: NuevoLibroFormState = NuevoLibroFormState(),
    val isGuardando: Boolean = false,
    val error: String? = null
)

class NuevoLibroViewModel : ViewModel() {

    private val repository = LibroRepository()

    private val _state = MutableStateFlow(NuevoLibroUiState())
    val state: StateFlow<NuevoLibroUiState> = _state

    fun onNombreChange(v: String) = _state.update { it.copy(form = it.form.copy(nombre = v, errorNombre = null)) }
    fun onNivelChange(v: String) = _state.update { it.copy(form = it.form.copy(nivel = v, errorNivel = null)) }
    fun onEdicionChange(v: String) = _state.update { it.copy(form = it.form.copy(edicion = v, errorEdicion = null)) }
    fun onTipoChange(v: String) = _state.update { it.copy(form = it.form.copy(tipo = v, errorTipo = null)) }
    fun onLoteChange(v: String) = _state.update { it.copy(form = it.form.copy(lote = v, errorLote = null)) }
    fun onStockChange(v: String) = _state.update { it.copy(form = it.form.copy(stockInicial = v, errorStock = null)) }
    fun onPrecioChange(v: String) = _state.update { it.copy(form = it.form.copy(precioUnitario = v)) }

    fun guardar(onExito: () -> Unit) {

        val formValidado = _state.value.form.validar()
        _state.update { it.copy(form = formValidado) }
        if (!formValidado.esValido()) return

        val precio = formValidado.precioUnitario.toDoubleOrNull()
        if (precio == null) {
            _state.update { it.copy(error = "Precio inválido") }
            return
        }

        viewModelScope.launch {

            _state.update { it.copy(isGuardando = true) }

            val request = CreateLibroRequest(
                nombre = formValidado.nombre,
                nivel = formValidado.nivel,
                tipo = formValidado.tipo.toIntOrNull() ?: 0,
                edicion = formValidado.edicion,
                lote = formValidado.lote.toIntOrNull() ?: 0,
                unidades = formValidado.stockInicial.toIntOrNull() ?: 0,
                valorCompra = 0.0,
                valorVentaPublico = precio
            )

            repository.crearLibro(request)
                .onSuccess {
                    _state.update { it.copy(isGuardando = false) }
                    onExito()
                }
                .onFailure { error ->
                    _state.update { state ->
                        state.copy(
                            isGuardando = false,
                            error = error.message ?: "Error al crear libro"
                        )
                    }
                }
        }
    }
}