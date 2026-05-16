package co.edu.cecar.smartbooks.data.DataClass.libro

import kotlinx.serialization.Serializable

@Serializable
data class
CreateLibroRequest(
    val nombre: String? = null,
    val nivel: String? = null,
    val tipo: Int = 0,
    val edicion: String? = null,
    val unidades: Int = 0,
    val lote: Int = 0,
    val valorCompra: Double = 0.0,
    val valorVentaPublico: Double = 0.0
)