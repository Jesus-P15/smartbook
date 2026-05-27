package co.edu.cecar.smartbooks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.edu.cecar.smartbooks.data.DataClass.cliente.UpdateClienteRequest
import co.edu.cecar.smartbooks.data.repository.ClienteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class EditarClienteUiState(
    val form: ClienteFormState = ClienteFormState(),
    val identificacion: String = "",
    val nombreOriginal: String = "",
    val isCargando: Boolean = true,
    val isGuardando: Boolean = false,
    val errorGeneral: String? = null,
    val mostrarConfirmarEliminar: Boolean = false
)

class EditarClienteViewModel : ViewModel() {

    private val repository = ClienteRepository()

    private val _state = MutableStateFlow(EditarClienteUiState())
    val state: StateFlow<EditarClienteUiState> = _state

    // ── Cargar cliente ─────────────────────────────────────────────────────────
    fun cargarCliente(identificacion: String) {
        viewModelScope.launch {
            _state.update { it.copy(isCargando = true) }

            repository.obtenerClientePorIdentificacion(identificacion)
                .onSuccess { cliente ->
                    _state.update {
                        it.copy(
                            identificacion = cliente.identificacion,
                            nombreOriginal = cliente.nombres,
                            isCargando = false,
                            form = ClienteFormState(
                                identificacion = cliente.identificacion,
                                nombres = cliente.nombres,
                                email = cliente.email,
                                celular = cliente.celular,
                                fechaNacimiento = cliente.fechaNacimiento
                            )
                        )
                    }
                }
                .onFailure { error ->
                    println("ERROR CARGAR CLIENTE: ${error.message}")
                    _state.update {
                        it.copy(
                            isCargando = false,
                            errorGeneral = error.message ?: "Error al cargar cliente"
                        )
                    }
                }
        }
    }

    // ── Changes ────────────────────────────────────────────────────────────────
    fun onNombresChange(v: String) = _state.update { it.copy(form = it.form.copy(nombres = v, errorNombres = null)) }
    fun onEmailChange(v: String) = _state.update { it.copy(form = it.form.copy(email = v, errorEmail = null)) }
    fun onCelularChange(v: String) = _state.update { it.copy(form = it.form.copy(celular = v, errorCelular = null)) }
    fun onFechaChange(v: String) = _state.update { it.copy(form = it.form.copy(fechaNacimiento = v, errorFecha = null)) }

    // ── Guardar ────────────────────────────────────────────────────────────────
    fun guardar(onExito: () -> Unit) {
        val formValidado = _state.value.form.validar()
        _state.update { it.copy(form = formValidado) }
        if (!formValidado.esValido()) return

        viewModelScope.launch {
            _state.update { it.copy(isGuardando = true) }

            val request = UpdateClienteRequest(
                nombres = formValidado.nombres,
                email = formValidado.email,
                celular = formValidado.celular,
                fechaNacimiento = formValidado.fechaNacimiento
            )

            repository.editarCliente(
                identificacion = _state.value.identificacion,
                request = request
            )
                .onSuccess {
                    _state.update { it.copy(isGuardando = false) }
                    onExito()
                }
                .onFailure { error ->
                    _state.update { state ->
                        state.copy(
                            isGuardando = false,
                            errorGeneral = error.message ?: "Error al guardar"
                        )
                    }
                }
        }
    }
}