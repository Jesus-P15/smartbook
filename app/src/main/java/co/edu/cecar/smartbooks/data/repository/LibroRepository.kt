package co.edu.cecar.smartbooks.data.repository

import co.edu.cecar.smartbooks.data.Constants.Constants
import co.edu.cecar.smartbooks.data.Constants.controlError.safeApiCall
import co.edu.cecar.smartbooks.data.DataClass.libro.CreateLibroRequest
import co.edu.cecar.smartbooks.data.DataClass.libro.LibroResponse
import co.edu.cecar.smartbooks.data.DataClass.libro.UpdateLibroRequest
import co.edu.cecar.smartbooks.data.network.HttpClientProvider
import co.edu.cecar.smartbooks.data.network.SessionManager
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class LibroRepository {

    private val client = HttpClientProvider.client
    private val baseUrl = "${Constants.BASE_URL}/api/Libros"

    suspend fun obtenerLibros(): Result<List<LibroResponse>> =
        safeApiCall {
            client.get(baseUrl) {
                header(HttpHeaders.Authorization, "Bearer ${SessionManager.obtenerToken()}")
            }.body()
        }

    suspend fun crearLibro(request: CreateLibroRequest): Result<LibroResponse> =
        safeApiCall {
            client.post(baseUrl) {
                header(HttpHeaders.Authorization, "Bearer ${SessionManager.obtenerToken()}")
                contentType(ContentType.Application.Json)
                setBody(request)
            }.body()
        }

    suspend fun editarLibro(id: Int, request: UpdateLibroRequest): Result<Unit> =
        safeApiCall {
            client.put("$baseUrl/$id") {
                header(HttpHeaders.Authorization, "Bearer ${SessionManager.obtenerToken()}")
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            Unit
        }

    suspend fun obtenerLibroPorId(id: Int): Result<LibroResponse> =
        safeApiCall {
            client.get("$baseUrl/$id") {
                header(HttpHeaders.Authorization, "Bearer ${SessionManager.obtenerToken()}")
            }.body()
        }
}