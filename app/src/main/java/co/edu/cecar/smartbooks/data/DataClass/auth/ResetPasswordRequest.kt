package co.edu.cecar.smartbooks.data.DataClass.auth

import kotlinx.serialization.Serializable

@Serializable
data class ResetPasswordRequest(
    val codigo: String? = null,
    val newPassword: String? = null
)