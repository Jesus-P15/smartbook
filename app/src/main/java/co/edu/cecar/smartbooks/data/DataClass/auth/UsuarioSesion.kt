package co.edu.cecar.smartbooks.data.DataClass.auth

import kotlinx.serialization.Serializable

@Serializable
data class UsuarioSesion(
    val id: Int,
    val identificacion: String,
    val nombres: String,
    val email: String,
    val rol: String,
    val activo: Boolean
)