package co.edu.cecar.smartbooks.data.DataClass.libro

import kotlinx.serialization.Serializable

@Serializable
data class CreateLibroRequest(
    val nombre: String,
    val nivel: String,
    val tipo: Int,
    val edicion: String,
    val unidades: Int = 0,
    val lote: Int = 0,
    val valorCompra: Double = 0.0,
    val valorVentaPublico: Double = 0.0
)