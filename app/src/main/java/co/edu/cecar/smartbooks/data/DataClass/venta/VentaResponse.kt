package co.edu.cecar.smartbooks.data.DataClass.venta

import kotlinx.serialization.Serializable

@Serializable
data class
VentaResponse(
    val id: Int,
    val numeroRecibo: String,
    val numeroComprobante: String,
    val total: Double,
    val fecha: String,
    val clienteNombre: String
)