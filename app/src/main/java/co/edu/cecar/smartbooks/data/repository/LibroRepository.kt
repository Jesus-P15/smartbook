package co.edu.cecar.smartbooks.data.repository

import co.edu.cecar.smartbooks.data.Constants.Constants
import co.edu.cecar.smartbooks.data.DataClass.libro.CreateLibroRequest
import co.edu.cecar.smartbooks.data.DataClass.libro.LibroResponse
import co.edu.cecar.smartbooks.data.DataClass.libro.UpdateLibroRequest
import co.edu.cecar.smartbooks.data.network.HttpClientProvider
import co.edu.cecar.smartbooks.data.network.SessionManager
import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.client.statement.bodyAsText
import io.ktor.http.*
import kotlinx.serialization.json.Json

class LibroRepository {

    private val client = HttpClientProvider.client
    private val baseUrl = "${Constants.BASE_URL}/api/Libros"

    suspend fun obtenerLibros(): Result<List<LibroResponse>> {
        return try {
            val response = client.get(baseUrl) {
                header(HttpHeaders.Authorization, "Bearer ${SessionManager.obtenerToken()}")
            }
            val texto = response.bodyAsText()
            println("JSON LISTA: $texto")
            val lista = Json.decodeFromString<List<LibroResponse>>(texto)
            Result.success(lista)
        } catch (e: Exception) {
            println("ERROR LISTA: ${e.message}")
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun crearLibro(request: CreateLibroRequest): Result<LibroResponse> {
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

    suspend fun editarLibro(
        id: Int,
        request: UpdateLibroRequest
    ): Result<Unit> {

        return try {

            client.put("$baseUrl/$id") {

                contentType(ContentType.Application.Json)

                header(
                    "Authorization",
                    "Bearer ${SessionManager.obtenerToken()}"
                )

                setBody(request)
            }

            Result.success(Unit)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun obtenerLibroPorId(id: Int): Result<LibroResponse> {
        return try {
            val response = client.get("$baseUrl/$id") {
                header(HttpHeaders.Authorization, "Bearer ${SessionManager.obtenerToken()}")
            }
            val texto = response.bodyAsText()
            println("STATUS: ${response.status}")
            println("JSON CRUDO: $texto")
            Result.success(response.body())
        } catch (e: Exception) {
            println("ERROR LIBRO: ${e.message}")
            e.printStackTrace()
            Result.failure(e)
        }
    }
}