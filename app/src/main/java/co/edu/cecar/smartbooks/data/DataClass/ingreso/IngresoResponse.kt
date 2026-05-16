package co.edu.cecar.smartbooks.data.DataClass.ingreso

import kotlinx.serialization.Serializable

@Serializable
data class IngresoResponse(
    val id: Int,
    val fecha: String,
    val lote: Int,
    val unidades: Int,
    val valorCompra: Double,
    val valorVentaPublico: Double
)