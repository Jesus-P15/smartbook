package co.edu.cecar.smartbooks.data.repository


import co.edu.cecar.smartbooks.data.Constants.Constants
import co.edu.cecar.smartbooks.data.DataClass.usuario.PerfilUsuarioResponse
import co.edu.cecar.smartbooks.data.DataClass.usuario.RegisterUsuarioRequest
import co.edu.cecar.smartbooks.data.DataClass.usuario.UpdateUsuarioRequest
import co.edu.cecar.smartbooks.data.DataClass.usuario.UsuarioResponse
import co.edu.cecar.smartbooks.data.network.HttpClientProvider
import co.edu.cecar.smartbooks.data.network.SessionManager
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class UsuarioRepository {

    private val client = HttpClientProvider.client
    private val baseUrl = "${Constants.BASE_URL}/api/Usuarios"

    // GET /api/Usuarios
    suspend fun obtenerUsuarios(): Result<List<UsuarioResponse>> = try {
        val response = client.get(baseUrl) {
            header(HttpHeaders.Authorization, "Bearer ${SessionManager.obtenerToken()}")
        }
        println("JSON USUARIOS: ${response.bodyAsText()}")
        Result.success(response.body())
    } catch (e: Exception) {
        println("ERROR USUARIOS: ${e.message}")
        Result.failure(e)
    }

    /* GET /api/Usuarios/{id}
    suspend fun obtenerUsuarioPorId(id: Int): Result<UsuarioResponse> = try {
        val response = client.get("$baseUrl/$id") {
            header(HttpHeaders.Authorization, "Bearer ${SessionManager.obtenerToken()}")
        }
        Result.success(response.body())
    } catch (e: Exception) {
        Result.failure(e)
    }*/

    // POST /api/Usuarios
    suspend fun crearUsuario(request: RegisterUsuarioRequest): Result<UsuarioResponse> = try {
        val response = client.post(baseUrl) {
            header(HttpHeaders.Authorization, "Bearer ${SessionManager.obtenerToken()}")
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        println("JSON CREAR USUARIO: ${response.bodyAsText()}")
        Result.success(response.body())
    } catch (e: Exception) {
        println("ERROR CREAR USUARIO: ${e.message}")
        Result.failure(e)
    }



    suspend fun actualizarUsuario(id: Int, request: UpdateUsuarioRequest): Result<Unit> = try {
        val response = client.put("$baseUrl/$id") {
            header(HttpHeaders.Authorization, "Bearer ${SessionManager.obtenerToken()}")
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        println("JSON ACTUALIZAR USUARIO: ${response.bodyAsText()}")
        Result.success(response.body())
    } catch (e: Exception) {
        println("ERROR ACTUALIZAR USUARIO: ${e.message}")
        Result.failure(e)
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

