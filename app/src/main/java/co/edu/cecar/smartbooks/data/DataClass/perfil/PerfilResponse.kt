package co.edu.cecar.smartbooks.data.DataClass.perfil

import kotlinx.serialization.Serializable

@Serializable
data class PerfilResponse(
    val id: Int,
    val nombres: String,
    val email: String,
    val rol: String
)