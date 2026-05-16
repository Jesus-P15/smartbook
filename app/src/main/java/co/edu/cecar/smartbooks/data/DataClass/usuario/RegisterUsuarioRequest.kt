package co.edu.cecar.smartbooks.data.DataClass.usuario

import kotlinx.serialization.Serializable

@Serializable
data class RegisterUsuarioRequest(
    val identificacion: String? = null,
    val nombres: String? = null,
    val email: String? = null,
    val password: String? = null,
    val rol: Int = 0
)