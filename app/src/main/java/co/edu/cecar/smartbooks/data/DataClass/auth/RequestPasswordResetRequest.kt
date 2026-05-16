package co.edu.cecar.smartbooks.data.DataClass.auth

import kotlinx.serialization.Serializable

@Serializable
data class RequestPasswordResetRequest(
    val email: String? = null
)