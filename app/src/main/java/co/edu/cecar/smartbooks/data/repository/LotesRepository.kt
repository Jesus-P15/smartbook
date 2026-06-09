package co.edu.cecar.smartbooks.data.repository

import co.edu.cecar.smartbooks.data.Constants.Constants
import co.edu.cecar.smartbooks.data.Constants.controlError.safeApiCall
import co.edu.cecar.smartbooks.data.DataClass.Lotes.CrearLoteRequest
import co.edu.cecar.smartbooks.data.DataClass.Lotes.LoteResponse
import co.edu.cecar.smartbooks.data.network.HttpClientProvider
import co.edu.cecar.smartbooks.data.network.SessionManager
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class LotesRepository {

    private val client = HttpClientProvider.client
    private val baseUrl = "${Constants.BASE_URL}/api/Lotes"

    suspend fun obtenerLotes(): Result<List<LoteResponse>> =
        safeApiCall {
            client.get(baseUrl) {
                header(HttpHeaders.Authorization, "Bearer ${SessionManager.obtenerToken()}")
            }.body()
        }

    suspend fun crearLote(lote: Int): Result<Unit> =
        safeApiCall {
            client.post(baseUrl) {
                header(HttpHeaders.Authorization, "Bearer ${SessionManager.obtenerToken()}")
                contentType(ContentType.Application.Json)
                setBody(CrearLoteRequest(lote = lote))
            }
            Unit
        }
}