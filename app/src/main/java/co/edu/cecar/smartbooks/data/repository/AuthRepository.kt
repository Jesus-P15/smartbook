package co.edu.cecar.smartbooks.data.repository

import co.edu.cecar.smartbooks.data.Constants.Constants
import co.edu.cecar.smartbooks.data.Constants.controlError.safeApiCall
import co.edu.cecar.smartbooks.data.DataClass.auth.LoginResponse
import co.edu.cecar.smartbooks.data.DataClass.auth.LoginUsuarioRequest
import co.edu.cecar.smartbooks.data.DataClass.auth.RestablecerContrasenaRequest
import co.edu.cecar.smartbooks.data.DataClass.auth.SolicitudRestablecimientoRequest
import co.edu.cecar.smartbooks.data.network.HttpClientProvider
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class AuthRepository {

    private val client = HttpClientProvider.client

    suspend fun login(request: LoginUsuarioRequest): Result<LoginResponse> =
        safeApiCall {
            client.post("${Constants.BASE_URL}/api/Seguridad/iniciar-sesion") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }.body<LoginResponse>()
        }

    suspend fun solicitarRestablecimiento(email: String): Result<Unit> =
        safeApiCall {
            client.post("${Constants.BASE_URL}/api/Seguridad/solicitar-restablecimiento") {
                contentType(ContentType.Application.Json)
                setBody(SolicitudRestablecimientoRequest(email = email))
            }
            Unit
        }

    suspend fun restablecerContrasena(codigo: String, nuevaContrasena: String): Result<Unit> =
        safeApiCall {
            client.post("${Constants.BASE_URL}/api/Seguridad/restablecer-contrasena") {
                contentType(ContentType.Application.Json)
                setBody(
                    RestablecerContrasenaRequest(
                        codigo = codigo,
                        newPassword = nuevaContrasena
                    )
                )
            }
            Unit
        }
}