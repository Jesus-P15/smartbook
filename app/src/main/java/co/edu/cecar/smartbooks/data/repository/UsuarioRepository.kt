package co.edu.cecar.smartbooks.data.repository

import co.edu.cecar.smartbooks.data.Constants.Constants
import co.edu.cecar.smartbooks.data.Constants.controlError.safeApiCall
import co.edu.cecar.smartbooks.data.DataClass.usuario.RegisterUsuarioRequest
import co.edu.cecar.smartbooks.data.DataClass.usuario.UpdateUsuarioRequest
import co.edu.cecar.smartbooks.data.DataClass.usuario.UsuarioResponse
import co.edu.cecar.smartbooks.data.network.HttpClientProvider
import co.edu.cecar.smartbooks.data.network.SessionManager
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class UsuarioRepository {

    private val client = HttpClientProvider.client
    private val baseUrl = "${Constants.BASE_URL}/api/Usuarios"

    suspend fun obtenerUsuarios(): Result<List<UsuarioResponse>> =
        safeApiCall {
            client.get(baseUrl) {
                header(HttpHeaders.Authorization, "Bearer ${SessionManager.obtenerToken()}")
            }.body()
        }

    suspend fun crearUsuario(request: RegisterUsuarioRequest): Result<UsuarioResponse> =
        safeApiCall {
            client.post(baseUrl) {
                header(HttpHeaders.Authorization, "Bearer ${SessionManager.obtenerToken()}")
                contentType(ContentType.Application.Json)
                setBody(request)
            }.body()
        }

    suspend fun actualizarUsuario(id: Int, request: UpdateUsuarioRequest): Result<Unit> =
        safeApiCall {
            client.put("$baseUrl/$id") {
                header(HttpHeaders.Authorization, "Bearer ${SessionManager.obtenerToken()}")
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            Unit
        }
}