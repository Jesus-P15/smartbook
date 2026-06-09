package co.edu.cecar.smartbooks.data.repository

import co.edu.cecar.smartbooks.data.Constants.Constants
import co.edu.cecar.smartbooks.data.Constants.controlError.safeApiCall
import co.edu.cecar.smartbooks.data.DataClass.cliente.ClienteResponse
import co.edu.cecar.smartbooks.data.DataClass.cliente.CreateClienteRequest
import co.edu.cecar.smartbooks.data.DataClass.cliente.UpdateClienteRequest
import co.edu.cecar.smartbooks.data.network.HttpClientProvider
import co.edu.cecar.smartbooks.data.network.SessionManager
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class ClienteRepository {

    private val client = HttpClientProvider.client
    private val baseUrl = "${Constants.BASE_URL}/api/Clientes"

    suspend fun obtenerClientes(): Result<List<ClienteResponse>> =
        safeApiCall {
            client.get(baseUrl) {
                header(HttpHeaders.Authorization, "Bearer ${SessionManager.obtenerToken()}")
            }.body()
        }

    suspend fun obtenerClientePorIdentificacion(identificacion: String): Result<ClienteResponse> =
        safeApiCall {
            client.get("$baseUrl/$identificacion") {
                header(HttpHeaders.Authorization, "Bearer ${SessionManager.obtenerToken()}")
            }.body()
        }

    suspend fun crearCliente(request: CreateClienteRequest): Result<ClienteResponse> =
        safeApiCall {
            client.post(baseUrl) {
                header(HttpHeaders.Authorization, "Bearer ${SessionManager.obtenerToken()}")
                contentType(ContentType.Application.Json)
                setBody(request)
            }.body()
        }

    suspend fun editarCliente(identificacion: String, request: UpdateClienteRequest): Result<Unit> =
        safeApiCall {
            client.put("$baseUrl/$identificacion") {
                header(HttpHeaders.Authorization, "Bearer ${SessionManager.obtenerToken()}")
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            Unit
        }
}