package co.edu.cecar.smartbooks.data.DataClass.auth

import co.edu.cecar.smartbooks.data.DataClass.UsuarioSesion
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val token: String,
    val usuario: UsuarioSesion
)