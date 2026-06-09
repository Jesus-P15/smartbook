package co.edu.cecar.smartbooks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.edu.cecar.smartbooks.data.DataClass.auth.LoginUsuarioRequest
import co.edu.cecar.smartbooks.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val loginExitoso: Boolean = false,
    val nombreUsuario: String = ""
)

class LoginViewModel : ViewModel() {

    private val repository = AuthRepository()

    private val _state = MutableStateFlow(LoginUiState())
    val state: StateFlow<LoginUiState> = _state.asStateFlow()

    fun resetear() {
        _state.value = LoginUiState()
    }
    fun onEmailChange(v: String)    = _state.update { it.copy(email = v, error = null) }
    fun onPasswordChange(v: String) = _state.update { it.copy(password = v, error = null) }

    fun login(onGuardarToken: (String) -> Unit) {
        if (_state.value.email.isBlank() || _state.value.password.isBlank()) {
            _state.update { it.copy(error = "Completa todos los campos") }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            repository.login(
                LoginUsuarioRequest(
                    email = _state.value.email.trim(),
                    password = _state.value.password
                )
            ).onSuccess { response ->
                onGuardarToken(response.token)
                _state.update {
                    it.copy(
                        isLoading = false,
                        loginExitoso = true,
                        nombreUsuario = response.usuario.nombres
                    )
                }
            }.onFailure { e ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Error al iniciar sesión"
                    )
                }
            }
        }
    }
}