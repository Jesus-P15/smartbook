package co.edu.cecar.smartbooks.data.repository


import co.edu.cecar.smartbooks.data.Constants.Constants
import co.edu.cecar.smartbooks.data.DataClass.auth.LoginResponse
import co.edu.cecar.smartbooks.data.DataClass.auth.LoginUsuarioRequest
import co.edu.cecar.smartbooks.data.network.HttpClientProvider
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody


class AuthRepository {

    private val client =
        HttpClientProvider.client

    suspend fun login(
        request: LoginUsuarioRequest
    ): Result<LoginResponse> = runCatching {

        client.post(
            "${Constants.BASE_URL}/api/Seguridad/iniciar-sesion"
        ) {

            setBody(request)

        }.body<LoginResponse>()
    }
    }