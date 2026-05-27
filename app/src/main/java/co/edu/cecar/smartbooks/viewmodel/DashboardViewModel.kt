package co.edu.cecar.smartbooks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.edu.cecar.smartbooks.data.DataClass.dashboard.DashboardResponse
import co.edu.cecar.smartbooks.data.repository.DashboardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {

    private val repository = DashboardRepository()

    private val _dashboard = MutableStateFlow<DashboardResponse?>(null)
    val dashboard: StateFlow<DashboardResponse?> = _dashboard

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        cargarDashboard()
    }

    fun cargarDashboard() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            repository.obtenerDashboard()
                .onSuccess { _dashboard.value = it }
                .onFailure { _error.value = it.message }
            _isLoading.value = false
        }
    }
}