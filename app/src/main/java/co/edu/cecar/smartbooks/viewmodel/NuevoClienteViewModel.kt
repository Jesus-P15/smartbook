package co.edu.cecar.smartbooks.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.edu.cecar.smartbooks.data.DataClass.cliente.CreateClienteRequest
import co.edu.cecar.smartbooks.data.repository.ClienteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ClienteFormState(
    val identificacion: String = "",
    val nombres: String = "",
    val email: String = "",
    val celular: String = "",
    val fechaNacimiento: String = "",

    val errorIdentificacion: String? = null,
    val errorNombres: String? = null,
    val errorEmail: String? = null,
    val errorCelular: String? = null,
    val errorFecha: String? = null
)

fun ClienteFormState.validar(): ClienteFormState = copy(
    errorIdentificacion = if (identificacion.isBlank()) "Requerido" else null,
    errorNombres = if (nombres.isBlank()) "Requerido" else null,
    errorEmail = if (email.isBlank() || !email.contains("@")) "Email inválido" else null,
    errorCelular = if (celular.isBlank()) "Requerido" else null,
    errorFecha = if (fechaNacimiento.isBlank()) "Selecciona una fecha" else null
)

fun ClienteFormState.esValido() =
    errorIdentificacion == null && errorNombres == null &&
            errorEmail == null && errorCelular == null && errorFecha == null

data class NuevoClienteUiState(
    val form: ClienteFormState = ClienteFormState(),
    val isGuardando: Boolean = false,
    val error: String? = null
)

class NuevoClienteViewModel : ViewModel() {

    private val repository = ClienteRepository()

    private val _state = MutableStateFlow(NuevoClienteUiState())
    val state: StateFlow<NuevoClienteUiState> = _state

    fun onIdentificacionChange(v: String) = _state.update { it.copy(form = it.form.copy(identificacion = v, errorIdentificacion = null)) }
    fun onNombresChange(v: String) = _state.update { it.copy(form = it.form.copy(nombres = v, errorNombres = null)) }
    fun onEmailChange(v: String) = _state.update { it.copy(form = it.form.copy(email = v, errorEmail = null)) }
    fun onCelularChange(v: String) = _state.update { it.copy(form = it.form.copy(celular = v, errorCelular = null)) }
    fun onFechaChange(v: String) = _state.update { it.copy(form = it.form.copy(fechaNacimiento = v, errorFecha = null)) }

    fun guardar(onExito: () -> Unit) {
        val formValidado = _state.value.form.validar()
        _state.update { it.copy(form = formValidado) }
        if (!formValidado.esValido()) return

        viewModelScope.launch {
            _state.update { it.copy(isGuardando = true) }

            val request = CreateClienteRequest(
                identificacion = formValidado.identificacion,
                nombres = formValidado.nombres,
                email = formValidado.email,
                celular = formValidado.celular,
                fechaNacimiento = formValidado.fechaNacimiento
            )

            repository.crearCliente(request)
                .onSuccess {
                    _state.update { it.copy(isGuardando = false) }
                    onExito()
                }
                .onFailure { error ->
                    _state.update { state ->
                        state.copy(isGuardando = false, error = error.message ?: "Error al crear cliente")
                    }
                }
        }
    }
}