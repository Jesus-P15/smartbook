package co.edu.cecar.smartbooks.data.DataClass.usuario

import kotlinx.serialization.Serializable

@Serializable
data class UsuarioResponse(
    val id: Int,
    val identificacion: String,
    val nombres: String,
    val email: String,
    val rol: String,
    val activo: Boolean
)