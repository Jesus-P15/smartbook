package co.edu.cecar.smartbooks.data.repository

import co.edu.cecar.smartbooks.data.Constants.Constants
import co.edu.cecar.smartbooks.data.DataClass.dashboard.DashboardResponse
import co.edu.cecar.smartbooks.data.network.HttpClientProvider.client
import co.edu.cecar.smartbooks.data.network.SessionManager
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders

class DashboardRepository {

    suspend fun obtenerDashboard(): Result<DashboardResponse> = runCatching {
        client.get("${Constants.BASE_URL}/api/Dashboard") {
            header(HttpHeaders.Authorization, "Bearer ${SessionManager.obtenerToken()}")
        }.body()
    }
}