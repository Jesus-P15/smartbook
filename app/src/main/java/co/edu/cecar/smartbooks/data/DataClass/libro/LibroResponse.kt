package co.edu.cecar.smartbooks.data.DataClass.libro

import kotlinx.serialization.Serializable

@Serializable
data class LibroResponse(
    val id: Int,
    val nombre: String,
    val nivel: String,
    val tipo: String,
    val lote: Int,
    val edicion: String,
    val stockTotal: Int,
    val valorCompa: Double,
    val valorVentaPulico: Double
)