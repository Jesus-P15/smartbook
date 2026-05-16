package co.edu.cecar.smartbooks.data.DataClass.cliente

import kotlinx.serialization.Serializable

@Serializable
data class CreateClienteRequest(
    val identificacion: String? = null,
    val nombres: String? = null,
    val email: String? = null,
    val celular: String? = null,
    val fechaNacimiento: String? = null
)