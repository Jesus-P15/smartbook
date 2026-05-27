package co.edu.cecar.smartbooks.data.repository



import co.edu.cecar.smartbooks.data.Constants.Constants
import co.edu.cecar.smartbooks.data.DataClass.venta.CreateVentaRequest
import co.edu.cecar.smartbooks.data.DataClass.venta.VentaResponse
import co.edu.cecar.smartbooks.data.network.HttpClientProvider
import co.edu.cecar.smartbooks.data.network.SessionManager
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class VentaRepository {

    private val client = HttpClientProvider.client
    private val baseUrl = "${Constants.BASE_URL}/api/Ventas"

    // ── GET /api/Ventas ────────────────────────────────────────────────────────
    suspend fun obtenerVentas(): Result<List<VentaResponse>> {
        return try {
            val response = client.get(baseUrl) {
                header(HttpHeaders.Authorization, "Bearer ${SessionManager.obtenerToken()}")
            }
            val texto = response.bodyAsText()
            println("JSON VENTAS: $texto")
            Result.success(response.body<List<VentaResponse>>())
        } catch (e: Exception) {
            println("ERROR VENTAS: ${e.message}")
            e.printStackTrace()
            Result.failure(e)
        }
    }

    // ── GET /api/Ventas/{id} ───────────────────────────────────────────────────
    suspend fun obtenerVentaPorId(id: Int): Result<VentaResponse> {
        return try {
            val response = client.get("$baseUrl/$id") {
                header(HttpHeaders.Authorization, "Bearer ${SessionManager.obtenerToken()}")
            }
            val texto = response.bodyAsText()
            println("JSON VENTA $id: $texto")
            Result.success(response.body<VentaResponse>())
        } catch (e: Exception) {
            println("ERROR VENTA $id: ${e.message}")
            e.printStackTrace()
            Result.failure(e)
        }
    }

    // ── POST /api/Ventas ───────────────────────────────────────────────────────
    suspend fun crearVenta(request: CreateVentaRequest): Result<VentaResponse> {
        return try {
            val response = client.post(baseUrl) {
                header(HttpHeaders.Authorization, "Bearer ${SessionManager.obtenerToken()}")
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            val texto = response.bodyAsText()
            println("JSON CREAR VENTA: $texto")
            Result.success(response.body<VentaResponse>())
        } catch (e: Exception) {
            println("ERROR CREAR VENTA: ${e.message}")
            e.printStackTrace()
            Result.failure(e)
        }
    }
}