package co.edu.cecar.smartbooks.viewmodel

import androidx.lifecycle.ViewModel
import co.edu.cecar.smartbooks.data.DataClass.perfil.PerfilResponse
import co.edu.cecar.smartbooks.data.network.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class PerfilUiState(
    val perfil: PerfilResponse? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class PerfilViewModel : ViewModel() {

    private val _state = MutableStateFlow(PerfilUiState())
    val state: StateFlow<PerfilUiState> = _state.asStateFlow()

    init {
        cargarPerfil()
    }

    fun cargarPerfil() {
        val perfil = SessionManager.obtenerPerfil()
        if (perfil != null) {
            _state.update { it.copy(perfil = perfil, isLoading = false) }
        } else {
            _state.update { it.copy(errorMessage = "No se pudo leer el perfil", isLoading = false) }
        }
    }
}