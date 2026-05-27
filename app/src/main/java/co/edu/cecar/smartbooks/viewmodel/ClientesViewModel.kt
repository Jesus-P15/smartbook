package co.edu.cecar.smartbooks.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.edu.cecar.smartbooks.data.DataClass.cliente.ClienteResponse
import co.edu.cecar.smartbooks.data.repository.ClienteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ClientesUiState(
    val clientes: List<ClienteResponse> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class ClientesViewModel : ViewModel() {

    private val repository = ClienteRepository()

    private val _state = MutableStateFlow(ClientesUiState())
    val state: StateFlow<ClientesUiState> = _state

    init {
        cargarClientes()
    }

    fun cargarClientes() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            repository.obtenerClientes()
                .onSuccess { lista ->
                    _state.update { it.copy(clientes = lista, isLoading = false) }
                }
                .onFailure { error ->
                    _state.update { it.copy(isLoading = false, error = error.message) }
                }
        }
    }
}