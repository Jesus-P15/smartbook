package co.edu.cecar.smartbooks.data.DataClass.ingreso

import kotlinx.serialization.Serializable

@Serializable
data class IngresoDetalle(
    val id: Int,
    val lote: Int,
    val fecha: String,
    val libroNombre: String,
    val nivel: String,
    val tipo: String,
    val unidades: Int,
    val valorCompra: Double,
    val valorVentaPublico: Double
)