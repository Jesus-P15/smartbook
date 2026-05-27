package co.edu.cecar.smartbooks.data.DataClass.auth

import kotlinx.serialization.Serializable

@Serializable
data class LoginUsuarioRequest(

    val email: String,
    val password: String
)