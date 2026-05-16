package co.edu.cecar.smartbooks.data.DataClass.libro

import kotlinx.serialization.Serializable

@Serializable
data class UpdateLibroRequest(
    val nombre: String? = null,
    val nivel: String? = null,
    val tipo: Int = 0,
    val edicion: String? = null
)