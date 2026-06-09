package co.edu.cecar.smartbooks.data.repository

import co.edu.cecar.smartbooks.data.Constants.Constants
import co.edu.cecar.smartbooks.data.Constants.controlError.safeApiCall
import co.edu.cecar.smartbooks.data.DataClass.dashboard.DashboardResponse
import co.edu.cecar.smartbooks.data.network.HttpClientProvider
import co.edu.cecar.smartbooks.data.network.SessionManager
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class DashboardRepository {

    private val client = HttpClientProvider.client

    suspend fun obtenerDashboard(): Result<DashboardResponse> =
        safeApiCall {
            client.get("${Constants.BASE_URL}/api/Dashboard") {
                header(HttpHeaders.Authorization, "Bearer ${SessionManager.obtenerToken()}")
            }.body()
        }
}