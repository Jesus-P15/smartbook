package co.edu.cecar.smartbooks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.edu.cecar.smartbooks.data.DataClass.libro.CreateLibroRequest
import co.edu.cecar.smartbooks.data.DataClass.libro.LibroResponse
import co.edu.cecar.smartbooks.data.DataClass.libro.UpdateLibroRequest
import co.edu.cecar.smartbooks.data.repository.LibroRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LibrosViewModel : ViewModel() {

    private val repository = LibroRepository()

    private val _libros = MutableStateFlow<List<LibroResponse>>(emptyList())
    val libros: StateFlow<List<LibroResponse>> = _libros

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        cargarLibros()
    }

    fun cargarLibros() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            repository.obtenerLibros()
                .onSuccess { _libros.value = it }
                .onFailure {
                    it.printStackTrace()
                    _error.value = it.message
                }
            _isLoading.value = false
        }
    }

    fun buscar(query: String) {
        if (query.isBlank()) { cargarLibros(); return }
        _libros.value = _libros.value.filter {
            it.nombre.contains(query, ignoreCase = true)
        }
    }
}
