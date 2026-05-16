package co.edu.cecar.smartbooks.data.DataClass.venta

import kotlinx.serialization.Serializable

@Serializable
data class CreateVentaItemRequest(
    val libroId: Int = 0,
    val lote: Int = 0,
    val cantidad: Int = 0
)