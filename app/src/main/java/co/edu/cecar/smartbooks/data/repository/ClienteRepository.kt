package co.edu.cecar.smartbooks.data.repository

import co.edu.cecar.smartbooks.data.Constants.Constants
import co.edu.cecar.smartbooks.data.DataClass.cliente.ClienteResponse
import co.edu.cecar.smartbooks.data.DataClass.cliente.CreateClienteRequest
import co.edu.cecar.smartbooks.data.DataClass.cliente.UpdateClienteRequest
import co.edu.cecar.smartbooks.data.network.HttpClientProvider
import co.edu.cecar.smartbooks.data.network.SessionManager
import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.client.statement.bodyAsText
import io.ktor.http.*

class ClienteRepository {

    private val client = HttpClientProvider.client
    private val baseUrl = "${Constants.BASE_URL}/api/Clientes"

    suspend fun obtenerClientes(): Result<List<ClienteResponse>> {
        return try {
            val response = client.get(baseUrl) {
                header(HttpHeaders.Authorization, "Bearer ${SessionManager.obtenerToken()}")
            }
            Result.success(response.body<List<ClienteResponse>>())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun obtenerClientePorIdentificacion(identificacion: String): Result<ClienteResponse> {
        return try {
            val response = client.get("$baseUrl/$identificacion") {
                header(HttpHeaders.Authorization, "Bearer ${SessionManager.obtenerToken()}")
            }
            val texto = response.bodyAsText()
            println("JSON CLIENTE: $texto")
            Result.success(response.body())
        } catch (e: Exception) {
            println("ERROR CLIENTE: ${e.message}")
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun crearCliente(request: CreateClienteRequest): Result<ClienteResponse> {
        return try {
            val response = client.post(baseUrl) {
                header(HttpHeaders.Authorization, "Bearer ${SessionManager.obtenerToken()}")
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            Result.success(response.body())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun editarCliente(
        identificacion: String,
        request: UpdateClienteRequest
    ): Result<Unit> {
        return try {
            val response = client.put("$baseUrl/$identificacion") {
                header(HttpHeaders.Authorization, "Bearer ${SessionManager.obtenerToken()}")
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            Result.success(response.body())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}