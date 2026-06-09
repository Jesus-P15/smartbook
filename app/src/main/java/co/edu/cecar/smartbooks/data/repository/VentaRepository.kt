package co.edu.cecar.smartbooks.data.repository

import co.edu.cecar.smartbooks.data.Constants.Constants
import co.edu.cecar.smartbooks.data.Constants.controlError.safeApiCall
import co.edu.cecar.smartbooks.data.DataClass.venta.CreateVentaRequest
import co.edu.cecar.smartbooks.data.DataClass.venta.VentaResponse
import co.edu.cecar.smartbooks.data.network.HttpClientProvider
import co.edu.cecar.smartbooks.data.network.SessionManager
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class VentaRepository {

    private val client = HttpClientProvider.client
    private val baseUrl = "${Constants.BASE_URL}/api/Ventas"

    suspend fun obtenerVentas(): Result<List<VentaResponse>> =
        safeApiCall {
            client.get(baseUrl) {
                header(HttpHeaders.Authorization, "Bearer ${SessionManager.obtenerToken()}")
            }.body()
        }

    suspend fun obtenerVentaPorId(id: Int): Result<VentaResponse> =
        safeApiCall {
            client.get("$baseUrl/$id") {
                header(HttpHeaders.Authorization, "Bearer ${SessionManager.obtenerToken()}")
            }.body()
        }

    suspend fun crearVenta(request: CreateVentaRequest): Result<VentaResponse> =
        safeApiCall {
            client.post(baseUrl) {
                header(HttpHeaders.Authorization, "Bearer ${SessionManager.obtenerToken()}")
                contentType(ContentType.Application.Json)
                setBody(request)
            }.body()
        }


    /* PATCH /api/Usuarios/{id}/estado
  suspend fun cambiarEstado(id: Int, activo: Boolean): Result<Unit> = try {
      val response = client.patch("$baseUrl/$id/estado") {
          header(HttpHeaders.Authorization, "Bearer ${SessionManager.obtenerToken()}")
          contentType(ContentType.Application.Json)
          setBody(mapOf("activo" to activo))
      }
      println("PATCH ESTADO USUARIO $id: ${response.status}")
      Result.success(Unit)
  } catch (e: Exception) {
      println("ERROR ESTADO USUARIO: ${e.message}")
      Result.failure(e)
  }*/

    /* GET /api/Usuarios/perfil
    suspend fun obtenerPerfil(): Result<PerfilUsuarioResponse> = try {
        val response = client.get("$baseUrl/perfil") {
            header(HttpHeaders.Authorization, "Bearer ${SessionManager.obtenerToken()}")
        }
        Result.success(response.body())
    } catch (e: Exception) {
        Result.failure(e)
    }*/
}