package co.edu.cecar.smartbooks.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel

import androidx.lifecycle.viewModelScope
import co.edu.cecar.smartbooks.data.DataClass.Lotes.LoteResponse
import co.edu.cecar.smartbooks.data.repository.LotesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LotesUiState(
    val lotes: List<LoteResponse> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val mostrarDialogoNuevo: Boolean = false,
    val nuevoLoteTexto: String = "",
    val isCreando: Boolean = false,
    val errorCrear: String? = null
)

class LotesViewModel : ViewModel() {

    private val repository = LotesRepository()

    private val _state = MutableStateFlow(LotesUiState())
    val state: StateFlow<LotesUiState> = _state.asStateFlow()

    init {
        Log.d("Lotes", "ViewModel creado")
        cargarLotes()
    }

    fun cargarLotes() {
        viewModelScope.launch {
            Log.d("Lotes", "Llamando API...")
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            repository.obtenerLotes()
                .onSuccess { lista ->
                    Log.d("Lotes", "Éxito: $lista")
                    _state.update { it.copy(lotes = lista, isLoading = false) }
                }
                .onFailure { error ->
                    Log.e("Lotes", "Error: ${error.message}", error)
                    _state.update { it.copy(isLoading = false, errorMessage = error.message) }
                }
        }
    }

   fun onNuevoLoteChange(valor: String) = _state.update { it.copy(nuevoLoteTexto = valor) }

    fun mostrarDialogo() = _state.update { it.copy(mostrarDialogoNuevo = true, nuevoLoteTexto = "", errorCrear = null) }
    fun ocultarDialogo() = _state.update { it.copy(mostrarDialogoNuevo = false, errorCrear = null) }

    fun crearLote(onExito: () -> Unit) {
        val numero = _state.value.nuevoLoteTexto.toIntOrNull() ?: return
        viewModelScope.launch {
            _state.update { it.copy(isCreando = true, errorCrear = null) }
            repository.crearLote(numero)
                .onSuccess {
                    _state.update { it.copy(isCreando = false, mostrarDialogoNuevo = false) }
                    cargarLotes()
                    onExito()
                }
                .onFailure { error ->
                    println("ERROR EN VIEWMODEL: ${error.message}")
                    _state.update { it.copy(isCreando = false, errorCrear = error.message) }
                }
        }
    }
}