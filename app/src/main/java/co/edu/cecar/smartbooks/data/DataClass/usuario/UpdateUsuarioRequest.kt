package co.edu.cecar.smartbooks.data.DataClass.usuario

import kotlinx.serialization.Serializable

@Serializable
data class UpdateUsuarioRequest(
    val nombres: String? = null,
    val email: String? = null,
    val rol: Int = 0,
    val activo: Boolean = true
)