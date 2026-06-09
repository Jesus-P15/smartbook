package co.edu.cecar.smartbooks.data.repository

import co.edu.cecar.smartbooks.data.Constants.Constants
import co.edu.cecar.smartbooks.data.Constants.controlError.safeApiCall
import co.edu.cecar.smartbooks.data.DataClass.inventarios.InventarioResponse
import co.edu.cecar.smartbooks.data.network.HttpClientProvider
import co.edu.cecar.smartbooks.data.network.SessionManager
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class InventarioRepository {

    private val client = HttpClientProvider.client
    private val baseUrl = "${Constants.BASE_URL}/api/Inventarios"

    suspend fun obtenerInventarios(): Result<List<InventarioResponse>> =
        safeApiCall {
            client.get(baseUrl) {
                header(HttpHeaders.Authorization, "Bearer ${SessionManager.obtenerToken()}")
            }.body()
        }
}