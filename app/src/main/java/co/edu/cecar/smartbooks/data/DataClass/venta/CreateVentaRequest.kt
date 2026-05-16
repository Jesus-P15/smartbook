package co.edu.cecar.smartbooks.data.DataClass.venta

import kotlinx.serialization.Serializable

@Serializable
data class CreateVentaRequest(
    val identificacionCliente: String? = null,
    val numeroComprobante: String? = null,
    val observaciones: String? = null,
    val items: List<CreateVentaItemRequest> = emptyList()
)