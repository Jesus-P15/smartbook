package co.edu.cecar.smartbooks.data.DataClass.usuario

import kotlinx.serialization.Serializable

@Serializable
data class PerfilUsuarioResponse(
    val id: Int,
    val nombres: String,
    val email: String,
    val rol: String
)