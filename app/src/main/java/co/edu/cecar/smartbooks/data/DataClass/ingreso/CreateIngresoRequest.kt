package co.edu.cecar.smartbooks.data.DataClass.ingreso

import kotlinx.serialization.Serializable

@Serializable
data class CreateIngresoRequest(
    val libroId: Int = 0,
    val unidades: Int = 0,
    val lote: Int = 0,
    val valorCompra: Double = 0.0,
    val valorVentaPublico: Double = 0.0
)