package co.edu.cecar.smartbooks.data.repository

import co.edu.cecar.smartbooks.data.Constants.Constants
import co.edu.cecar.smartbooks.data.DataClass.inventarios.InventarioResponse
import co.edu.cecar.smartbooks.data.network.HttpClientProvider
import co.edu.cecar.smartbooks.data.network.SessionManager
import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.client.statement.bodyAsText
import io.ktor.http.*

class InventarioRepository {

    private val client = HttpClientProvider.client
    private val baseUrl = "${Constants.BASE_URL}/api/Inventarios"

    suspend fun obtenerInventarios(): Result<List<InventarioResponse>> {
        return try {
            val response = client.get(baseUrl) {
                header(HttpHeaders.Authorization, "Bearer ${SessionManager.obtenerToken()}")
            }
            val texto = response.bodyAsText()
            println("JSON INVENTARIOS: $texto")
            Result.success(response.body<List<InventarioResponse>>())
        } catch (e: Exception) {
            println("ERROR INVENTARIOS: ${e.message}")
            e.printStackTrace()
            Result.failure(e)
        }
    }
}