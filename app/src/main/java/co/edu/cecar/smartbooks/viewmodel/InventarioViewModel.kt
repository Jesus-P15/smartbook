package co.edu.cecar.smartbooks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.edu.cecar.smartbooks.data.DataClass.inventarios.InventarioResponse
import co.edu.cecar.smartbooks.data.repository.InventarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar

data class InventariosUiState(
    val inventarios: List<InventarioResponse> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val loteFiltro: String = "",
    val loteSeleccionado: Int? = null,

    // Totales
    val totalLibros: Int = 0,
    val bajoStock: Int = 0,
    val stockTotal: Int = 0
)

class InventariosViewModel : ViewModel() {

    private val repository = InventarioRepository()

    private val _state = MutableStateFlow(InventariosUiState())
    val state: StateFlow<InventariosUiState> = _state

    // Lotes sugeridos basados en semestres actuales
    val lotesSugeridos: List<Int> get() {
        val year = Calendar.getInstance().get(Calendar.YEAR)
        return listOf(
            (year - 1) * 10 + 2,  // Sem 2 año anterior
            year * 10 + 1,         // Sem 1 año actual
            year * 10 + 2          // Sem 2 año actual
        )
    }

    init {
        cargarInventarios()
    }

    fun cargarInventarios() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            repository.obtenerInventarios()
                .onSuccess { lista ->
                    _state.update {
                        it.copy(
                            inventarios = lista,
                            isLoading = false,
                            totalLibros = lista.size,
                            bajoStock = lista.count { inv -> inv.stockDisponible in 1..5 },
                            stockTotal = lista.sumOf { inv -> inv.stockDisponible }
                        )
                    }
                }
                .onFailure { error ->
                    _state.update { it.copy(isLoading = false, error = error.message) }
                }
        }
    }

    fun onLoteFiltroChange(v: String) = _state.update { it.copy(loteFiltro = v) }

    fun seleccionarLote(lote: Int) = _state.update {
        it.copy(loteSeleccionado = lote, loteFiltro = lote.toString())
    }

    fun limpiarFiltro() = _state.update { it.copy(loteSeleccionado = null, loteFiltro = "") }

    fun inventariosFiltrados(): List<InventarioResponse> {
        val state = _state.value
        val lote = state.loteFiltro.toIntOrNull()
        return if (lote != null) {
            state.inventarios.filter { it.lote == lote }
        } else {
            state.inventarios
        }
    }
}