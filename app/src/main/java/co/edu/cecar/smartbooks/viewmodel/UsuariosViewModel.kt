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


    // Campos formulario editar usuario
    private val _editNombres = MutableStateFlow("")
    val editNombres: StateFlow<String> = _editNombres.asStateFlow()

    private val _editEmail = MutableStateFlow("")
    val editEmail: StateFlow<String> = _editEmail.asStateFlow()

    private val _editRol = MutableStateFlow("Vendedor")
    val editRol: StateFlow<String> = _editRol.asStateFlow()

    private val _editActivo = MutableStateFlow(true)
    val editActivo: StateFlow<Boolean> = _editActivo.asStateFlow()

    private val _rol = MutableStateFlow("Vendedor")
    val rol: StateFlow<String> = _rol.asStateFlow()



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
        _rol.value = "Vendedor"
        _state.update { it.copy(mostrarNuevoUsuario = true, errorCrear = null, usuarioCreadoExito = false) }
    }

    fun cerrarNuevoUsuario() {
        _state.update { it.copy(mostrarNuevoUsuario = false, errorCrear = null) }
        cargarUsuarios()
    }

    fun onIdentificacionChange(v: String) { _identificacion.value = v }
    fun onNombresChange(v: String)        { _nombres.value = v }
    fun onEmailChange(v: String)          { _email.value = v }
    fun onPasswordChange(v: String)       { _password.value = v }

    fun onRolChange(v: String) { _rol.value = v }


    fun crearUsuario() {


        val rolId = when (_rol.value) {
            "Admin" -> 1
            "Vendedor" -> 2
            else -> 2
        }

        if (_identificacion.value.isBlank() || _nombres.value.isBlank() ||
            _email.value.isBlank() || _password.value.isBlank()) {
            _state.update { it.copy(errorCrear = "Todos los campos son requeridos") }
            return
        }
        if (_password.value.length < 8) {
            _state.update { it.copy(errorCrear = "La contraseña debe tener al menos 8 caracteres") }
            return
        }
        if (_identificacion.value.length < 6) {
            _state.update { it.copy(errorCrear = "La identificación debe tener al menos 6 dígitos") }
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
                    rol = rolId
                )
            ).onSuccess {
                _state.update { it.copy(creandoUsuario = false, usuarioCreadoExito = true) }
                cargarUsuarios()
            }.onFailure { e ->
                _state.update { it.copy(creandoUsuario = false, errorCrear = e.message ?: "Error al crear usuario") }
            }
        }
    }

    // ── Editar usuario ─────────────────────────────────────────────────────────
    fun abrirEditarUsuario(usuario: UsuarioResponse) {
        _editNombres.value = usuario.nombres
        _editEmail.value = usuario.email
        _editRol.value = usuario.rol
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

    fun cerrarEditarUsuario() {
        _state.update { it.copy(mostrarEditarUsuario = false, errorEditar = null) }
        cargarUsuarios()
    }
    fun onEditNombresChange(v: String) { _editNombres.value = v }
    fun onEditEmailChange(v: String)   { _editEmail.value = v }
    fun onEditRolChange(v: String) { _editRol.value = v }
    fun onEditActivoChange(v: Boolean) { _editActivo.value = v }

    fun actualizarUsuario() {

        val rolId = when (_editRol.value) {
            "Admin" -> 1
            "Vendedor" -> 2
            else -> 2
        }

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
                    rol = rolId,
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