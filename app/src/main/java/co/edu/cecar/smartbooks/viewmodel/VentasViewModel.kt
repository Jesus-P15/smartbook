package co.edu.cecar.smartbooks.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.edu.cecar.smartbooks.data.DataClass.Lotes.LoteResponse
import co.edu.cecar.smartbooks.data.DataClass.libro.LibroResponse
import co.edu.cecar.smartbooks.data.DataClass.usuario.UsuarioResponse
import co.edu.cecar.smartbooks.data.DataClass.venta.CreateVentaItemRequest
import co.edu.cecar.smartbooks.data.DataClass.venta.CreateVentaRequest
import co.edu.cecar.smartbooks.data.DataClass.venta.VentaResponse
import co.edu.cecar.smartbooks.data.repository.LibroRepository
import co.edu.cecar.smartbooks.data.repository.LotesRepository
import co.edu.cecar.smartbooks.data.repository.VentaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// ── Estado UI ──────────────────────────────────────────────────────────────────
data class VentasUiState(
    val ventas: List<VentaResponse> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    // Filtros
    val fechaDesde: String = "",
    val fechaHasta: String = "",
    val busqueda: String = "",
    // Detalle
    val ventaSeleccionada: VentaResponse? = null,
    val cargandoDetalle: Boolean = false,
    val mostrarDetalle: Boolean = false,
    // Nueva venta
    val mostrarNuevaVenta: Boolean = false,
    val creandoVenta: Boolean = false,
    val errorCrear: String? = null,
    val ventaCreadaExito: Boolean = false,

    val libros: List<LibroResponse> = emptyList(),
    val lotes: List<LoteResponse> = emptyList(),
    val busquedaLibro: String = ""
)

data class ItemVentaUi(
    val libroId: Int = 0,
    val lote: Int = 0,
    val cantidad: Int = 1
)

// ── ViewModel ──────────────────────────────────────────────────────────────────
class VentasViewModel : ViewModel() {



    private val repository = VentaRepository()

    private val _state = MutableStateFlow(VentasUiState())
    val state: StateFlow<VentasUiState> = _state.asStateFlow()

    private val _identificacionCliente = MutableStateFlow("")
    val identificacionCliente: StateFlow<String> = _identificacionCliente.asStateFlow()

    private val _numeroComprobante = MutableStateFlow("")
    val numeroComprobante: StateFlow<String> = _numeroComprobante.asStateFlow()

    private val _observaciones = MutableStateFlow("")
    val observaciones: StateFlow<String> = _observaciones.asStateFlow()

    private val _items = MutableStateFlow<List<ItemVentaUi>>(listOf(ItemVentaUi()))
    val items: StateFlow<List<ItemVentaUi>> = _items.asStateFlow()

    private val librosRepository = LibroRepository()
    private val lotesRepository = LotesRepository()


    // ── Carga ──────────────────────────────────────────────────────────────────
    fun cargarVentas() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            repository.obtenerVentas()
                .onSuccess { ventas ->
                    _state.update { it.copy(ventas = ventas, isLoading = false) }
                }
                .onFailure { e ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = "Error al cargar ventas: ${e.message}"
                        )
                    }
                }
        }
    }

    // ── Filtros ────────────────────────────────────────────────────────────────
    fun onFechaDesdeChange(value: String) = _state.update { it.copy(fechaDesde = value) }
    fun onFechaHastaChange(value: String) = _state.update { it.copy(fechaHasta = value) }
    fun onBusquedaChange(value: String)   = _state.update { it.copy(busqueda = value) }

    init {
        cargarLibros()
        cargarLotes()
    }

    fun cargarLibros() {
        viewModelScope.launch {
            librosRepository.obtenerLibros()
                .onSuccess { lista -> _state.update { it.copy(libros = lista) } }
                .onFailure { }
        }
    }

    fun cargarLotes() {
        viewModelScope.launch {
            lotesRepository.obtenerLotes()
                .onSuccess { lista -> _state.update { it.copy(lotes = lista) } }
                .onFailure { }
        }
    }

    fun onBusquedaLibroChange(v: String) = _state.update { it.copy(busquedaLibro = v) }

    fun librosFiltrados(): List<LibroResponse> {
        val s = _state.value
        if (s.busquedaLibro.isBlank()) return s.libros
        return s.libros.filter { it.nombre.contains(s.busquedaLibro, ignoreCase = true) }
    }



    fun limpiarFiltros() = _state.update {
        it.copy(fechaDesde = "", fechaHasta = "", busqueda = "")
    }

    fun ventasFiltradas(): List<VentaResponse> {
        val s = _state.value
        return s.ventas.filter { venta ->
            val coincideBusqueda = s.busqueda.isBlank() ||
                    venta.clienteNombre?.contains(s.busqueda, ignoreCase = true) == true ||
                    venta.numeroRecibo?.contains(s.busqueda, ignoreCase = true) == true
            val coincideDesde = s.fechaDesde.isBlank() ||
                    (venta.fecha?.compareTo(s.fechaDesde) ?: 0) >= 0
            val coincideHasta = s.fechaHasta.isBlank() ||
                    (venta.fecha?.compareTo(s.fechaHasta) ?: 0) <= 0
            coincideBusqueda && coincideDesde && coincideHasta
        }
    }

    // ── Detalle ────────────────────────────────────────────────────────────────
    fun verDetalle(venta: VentaResponse) {
        _state.update { it.copy(ventaSeleccionada = venta, mostrarDetalle = true) }
        venta.id?.let { cargarDetalle(it) }
    }

    private fun cargarDetalle(id: Int) {
        viewModelScope.launch {
            _state.update { it.copy(cargandoDetalle = true) }
            repository.obtenerVentaPorId(id)
                .onSuccess { detalle ->
                    _state.update { it.copy(ventaSeleccionada = detalle, cargandoDetalle = false) }
                }
                .onFailure {
                    _state.update { it.copy(cargandoDetalle = false) }
                }
        }
    }

    fun cerrarDetalle() =
        _state.update { it.copy(mostrarDetalle = false, ventaSeleccionada = null) }

    // ── Nueva venta ────────────────────────────────────────────────────────────
    fun abrirNuevaVenta() {
        _identificacionCliente.value = ""
        _numeroComprobante.value = ""
        _observaciones.value = ""
        _items.value = listOf(ItemVentaUi())
        _state.update {
            it.copy(mostrarNuevaVenta = true, errorCrear = null, ventaCreadaExito = false)
        }
    }

    fun cerrarNuevaVenta() =
        _state.update { it.copy(mostrarNuevaVenta = false, errorCrear = null) }

    fun onIdentificacionChange(v: String) { _identificacionCliente.value = v }
    fun onComprobanteChange(v: String)    { _numeroComprobante.value = v }
    fun onObservacionesChange(v: String)  { _observaciones.value = v }

    fun onItemChange(index: Int, item: ItemVentaUi) {
        _items.update { list -> list.toMutableList().also { it[index] = item } }
    }

    fun agregarItem() {
        _items.update { it + ItemVentaUi() }
    }

    fun eliminarItem(index: Int) {
        if (_items.value.size > 1)
            _items.update { it.toMutableList().also { list -> list.removeAt(index) } }
    }

    fun crearVenta() {
        val identificacion = _identificacionCliente.value.trim()

        if (identificacion.isBlank()) {
            _state.update { it.copy(errorCrear = "La identificación del cliente es requerida") }
            return
        }
        if (_items.value.any { it.libroId == 0 || it.lote == 0 || it.cantidad <= 0 }) {
            _state.update { it.copy(errorCrear = "Completa todos los campos de los ítems") }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(creandoVenta = true, errorCrear = null) }

            val request = CreateVentaRequest(
                identificacionCliente = identificacion,
                numeroComprobante = _numeroComprobante.value.trim().ifBlank { null },
                observaciones = _observaciones.value.trim().ifBlank { null },
                items = _items.value.map {
                    CreateVentaItemRequest(
                        libroId = it.libroId,
                        lote = it.lote,
                        cantidad = it.cantidad
                    )
                }
            )

            repository.crearVenta(request)
                .onSuccess {
                    _state.update { it.copy(creandoVenta = false, ventaCreadaExito = true) }
                    cargarVentas()
                }
                .onFailure { e ->
                    _state.update {
                        it.copy(
                            creandoVenta = false,
                            errorCrear = "Error al crear venta: ${e.message}"
                        )
                    }
                }
        }
    }
}
