package co.edu.cecar.smartbooks.data.DataClass.auth

import kotlinx.serialization.Serializable

@Serializable
data class RestablecerContrasenaRequest(
    val codigo: String,
    val newPassword: String
)