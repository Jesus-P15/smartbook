package co.edu.cecar.smartbooks.data.DataClass.venta

import kotlinx.serialization.Serializable

@Serializable
data class VentaResponse(
    val id: Int? = null,
    val numeroRecibo: String? = null,
    val numeroComprobante: String? = null,
    val total: Double? = null,
    val fecha: String? = null,
    val clienteNombre: String? = null
)
