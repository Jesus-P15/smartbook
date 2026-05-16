package co.edu.cecar.smartbooks.data.DataClass.cliente

import kotlinx.serialization.Serializable

@Serializable
data class ClienteResponse(
    val id: Int,
    val identificacion: String,
    val nombres: String,
    val email: String,
    val celular: String,
    val fechaNacimiento: String
)