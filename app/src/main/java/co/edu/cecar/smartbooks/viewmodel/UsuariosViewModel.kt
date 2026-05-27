package co.edu.cecar.smartbooks.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.edu.cecar.smartbooks.data.DataClass.usuario.RegisterUsuarioRequest
import co.edu.cecar.smartbooks.data.DataClass.usuario.UpdateUsuarioRequest
import co.edu.cecar.smartbooks.data.DataClass.usuario.UsuarioResponse
import co.edu.cecar.smartbooks.data.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// ── Estado UI ──────────────────────────────────────────────────────────────────
data class UsuariosUiState(
    val usuarios: List<UsuarioResponse> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    // Filtros
    val busqueda: String = "",
    // Nuevo usuario
    val mostrarNuevoUsuario: Boolean = false,
    val creandoUsuario: Boolean = false,
    val errorCrear: String? = null,
    val usuarioCreadoExito: Boolean = false,
    // Editar usuario
    val usuarioAEditar: UsuarioResponse? = null,
    val mostrarEditarUsuario: Boolean = false,
    val actualizandoUsuario: Boolean = false,
    val errorEditar: String? = null,
    val usuarioEditadoExito: Boolean = false
)

// ── ViewModel ──────────────────────────────────────────────────────────────────
class UsuariosViewModel : ViewModel() {

    private val repository = UsuarioRepository()

    private val _state = MutableStateFlow(UsuariosUiState())
    val state: StateFlow<UsuariosUiState> = _state.asStateFlow()

    // Campos formulario nuevo usuario
    private val _identificacion = MutableStateFlow("")
    val identificacion: StateFlow<String> = _identificacion.asStateFlow()

    private val _nombres = MutableStateFlow("")
    val nombres: StateFlow<String> = _nombres.asStateFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _rol = MutableStateFlow(0) // 0=Admin, 1=Vendedor
    val rol: StateFlow<Int> = _rol.asStateFlow()

    // Campos formulario editar usuario
    private val _editNombres = MutableStateFlow("")
    val editNombres: StateFlow<String> = _editNombres.asStateFlow()

    private val _editEmail = MutableStateFlow("")
    val editEmail: StateFlow<String> = _editEmail.asStateFlow()

    private val _editRol = MutableStateFlow(0)
    val editRol: StateFlow<Int> = _editRol.asStateFlow()

    private val _editActivo = MutableStateFlow(true)
    val editActivo: StateFlow<Boolean> = _editActivo.asStateFlow()

    // ── Carga ──────────────────────────────────────────────────────────────────
    fun cargarUsuarios() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            repository.obtenerUsuarios()
                .onSuccess { lista ->
                    _state.update { it.copy(usuarios = lista, isLoading = false) }
                }
                .onFailure { e ->
                    _state.update { it.copy(isLoading = false, error = "Error al cargar usuarios: ${e.message}") }
                }
        }
    }

    // ── Filtros ────────────────────────────────────────────────────────────────
    fun onBusquedaChange(v: String) = _state.update { it.copy(busqueda = v) }

    fun usuariosFiltrados(): List<UsuarioResponse> {
        val s = _state.value
        if (s.busqueda.isBlank()) return s.usuarios
        return s.usuarios.filter { u ->
            u.nombres.contains(s.busqueda, ignoreCase = true) ||
                    u.identificacion.contains(s.busqueda, ignoreCase = true) ||
                    u.email.contains(s.busqueda, ignoreCase = true)
        }
    }

    // ── Nuevo usuario ──────────────────────────────────────────────────────────
    fun abrirNuevoUsuario() {
        _identificacion.value = ""
        _nombres.value = ""
        _email.value = ""
        _password.value = ""
        _rol.value = 1
        _state.update { it.copy(mostrarNuevoUsuario = true, errorCrear = null, usuarioCreadoExito = false) }
    }

    fun cerrarNuevoUsuario() = _state.update { it.copy(mostrarNuevoUsuario = false, errorCrear = null) }

    fun onIdentificacionChange(v: String) { _identificacion.value = v }
    fun onNombresChange(v: String)        { _nombres.value = v }
    fun onEmailChange(v: String)          { _email.value = v }
    fun onPasswordChange(v: String)       { _password.value = v }
    fun onRolChange(v: Int)               { _rol.value = v }

    fun crearUsuario() {
        if (_identificacion.value.isBlank() || _nombres.value.isBlank() ||
            _email.value.isBlank() || _password.value.isBlank()) {
            _state.update { it.copy(errorCrear = "Todos los campos son requeridos") }
            return
        }
        viewModelScope.launch {
            _state.update { it.copy(creandoUsuario = true, errorCrear = null) }
            repository.crearUsuario(
                RegisterUsuarioRequest(
                    identificacion = _identificacion.value.trim(),
                    nombres = _nombres.value.trim(),
                    email = _email.value.trim(),
                    password = _password.value,
                    rol = _rol.value
                )
            ).onSuccess {
                _state.update { it.copy(creandoUsuario = false, usuarioCreadoExito = true) }
                cargarUsuarios()
            }.onFailure { e ->
                _state.update { it.copy(creandoUsuario = false, errorCrear = "Error: ${e.message}") }
            }
        }
    }

    // ── Editar usuario ─────────────────────────────────────────────────────────
    fun abrirEditarUsuario(usuario: UsuarioResponse) {
        _editNombres.value = usuario.nombres
        _editEmail.value = usuario.email
        _editRol.value = if (usuario.rol.equals("Admin", ignoreCase = true)) 0 else 1
        _editActivo.value = usuario.activo
        _state.update {
            it.copy(
                usuarioAEditar = usuario,
                mostrarEditarUsuario = true,
                errorEditar = null,
                usuarioEditadoExito = false
            )
        }
    }

    fun cerrarEditarUsuario() = _state.update { it.copy(mostrarEditarUsuario = false, errorEditar = null) }

    fun onEditNombresChange(v: String) { _editNombres.value = v }
    fun onEditEmailChange(v: String)   { _editEmail.value = v }
    fun onEditRolChange(v: Int)        { _editRol.value = v }
    fun onEditActivoChange(v: Boolean) { _editActivo.value = v }

    fun actualizarUsuario() {
        val usuario = _state.value.usuarioAEditar ?: return
        if (_editNombres.value.isBlank() || _editEmail.value.isBlank()) {
            _state.update { it.copy(errorEditar = "Nombre y email son requeridos") }
            return
        }
        viewModelScope.launch {
            _state.update { it.copy(actualizandoUsuario = true, errorEditar = null) }
            repository.actualizarUsuario(
                id = usuario.id,
                request = UpdateUsuarioRequest(
                    nombres = _editNombres.value.trim(),
                    email = _editEmail.value.trim(),
                    rol = _editRol.value,
                    activo = _editActivo.value
                )
            ).onSuccess {
                _state.update { it.copy(actualizandoUsuario = false, usuarioEditadoExito = true) }
                cargarUsuarios()
            }.onFailure { e ->
                _state.update {
                    it.copy(actualizandoUsuario = false, errorEditar = "Error: ${e.message}")
                }
            }
        }
    }
}