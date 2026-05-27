package co.edu.cecar.smartbooks.data.DataClass.dashboard


import co.edu.cecar.smartbooks.data.DataClass.venta.VentaResponse
import kotlinx.serialization.Serializable

@Serializable
data class DashboardResponse(

    val totalLibros: Int,

    val totalClientes: Int,

    val cantVentasMes: Int,

    val totalVentasMes: Double,

    val ventasHoy: List<VentaResponse> = emptyList()
)