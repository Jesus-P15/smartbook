package co.edu.cecar.smartbooks.data.DataClass.libro

import kotlinx.serialization.Serializable

@Serializable
data class LibroDetalle(
    val id: Int,
    val nombre: String,
    val nivel: String,
    val tipo: Int,
    val edicion: String
)