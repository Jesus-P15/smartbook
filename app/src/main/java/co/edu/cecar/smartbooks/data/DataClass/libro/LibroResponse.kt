package co.edu.cecar.smartbooks.data.DataClass.libro

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LibroResponse(
    val id: Int,
    val nombre: String,
    val nivel: String,
    val tipo: String,
    val lote: Int = 0,
    val edicion: String,
    val stockTotal: Int = 0,

    @SerialName("valorCompa")
    val valorCompra: Int = 0,

    @SerialName("valorVentaPulico")
    val valorVentaPublico: Int = 0
)