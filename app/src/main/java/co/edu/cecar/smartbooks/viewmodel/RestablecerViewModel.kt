package co.edu.cecar.smartbooks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.edu.cecar.smartbooks.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SolicitudUiState(
    val email: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val exito: Boolean = false
)

data class RestablecerUiState(
    val codigo: String = "",
    val nuevaContrasena: String = "",
    val confirmarContrasena: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val exito: Boolean = false
)

class RestablecerViewModel : ViewModel() {

    private val repository = AuthRepository()

    private val _solicitudState = MutableStateFlow(SolicitudUiState())
    val solicitudState: StateFlow<SolicitudUiState> = _solicitudState.asStateFlow()

    private val _restablecerState = MutableStateFlow(RestablecerUiState())
    val restablecerState: StateFlow<RestablecerUiState> = _restablecerState.asStateFlow()

    // ── Solicitud ──────────────────────────────────────────────────────────────
    fun onEmailChange(v: String) = _solicitudState.update { it.copy(email = v, error = null) }

    fun solicitarRestablecimiento() {
        if (_solicitudState.value.email.isBlank() ||
            !_solicitudState.value.email.contains("@")) {
            _solicitudState.update { it.copy(error = "Ingresa un email válido") }
            return
        }
        viewModelScope.launch {
            _solicitudState.update { it.copy(isLoading = true, error = null) }
            repository.solicitarRestablecimiento(_solicitudState.value.email.trim())
                .onSuccess {
                    _solicitudState.update { it.copy(isLoading = false, exito = true) }
                }
                .onFailure { e ->
                    _solicitudState.update {
                        it.copy(isLoading = false, error = e.message ?: "Error al enviar solicitud")
                    }
                }
        }
    }

    // ── Restablecer ────────────────────────────────────────────────────────────
    fun onCodigoChange(v: String)            = _restablecerState.update { it.copy(codigo = v, error = null) }
    fun onNuevaContrasenaChange(v: String)   = _restablecerState.update { it.copy(nuevaContrasena = v, error = null) }
    fun onConfirmarContrasenaChange(v: String) = _restablecerState.update { it.copy(confirmarContrasena = v, error = null) }

    fun restablecerContrasena(onExito: () -> Unit) {
        val s = _restablecerState.value
        if (s.codigo.isBlank()) {
            _restablecerState.update { it.copy(error = "El código es requerido") }
            return
        }
        if (s.nuevaContrasena.length < 8) {
            _restablecerState.update { it.copy(error = "La contraseña debe tener al menos 8 caracteres") }
            return
        }
        if (s.nuevaContrasena != s.confirmarContrasena) {
            _restablecerState.update { it.copy(error = "Las contraseñas no coinciden") }
            return
        }
        viewModelScope.launch {
            _restablecerState.update { it.copy(isLoading = true, error = null) }
            repository.restablecerContrasena(s.codigo.trim(), s.nuevaContrasena)
                .onSuccess {
                    _restablecerState.update { it.copy(isLoading = false, exito = true) }
                    onExito()
                }
                .onFailure { e ->
                    _restablecerState.update {
                        it.copy(isLoading = false, error = e.message ?: "Error al restablecer")
                    }
                }
        }
    }
}