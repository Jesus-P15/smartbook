package co.edu.cecar.smartbooks.data.DataClass.cliente

import kotlinx.serialization.Serializable

@Serializable
data class UpdateClienteRequest(
    val nombres: String? = null,
    val email: String? = null,
    val celular: String? = null,
    val fechaNacimiento: String? = null
)