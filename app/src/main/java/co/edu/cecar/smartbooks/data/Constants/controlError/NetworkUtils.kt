package co.edu.cecar.smartbooks.data.Constants.controlError

import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.*

suspend fun <T> safeApiCall(block: suspend () -> T): Result<T> {
    return try {
        Result.success(block())
    } catch (e: io.ktor.client.plugins.ClientRequestException) {
        val body = e.response.bodyAsText()
        println("API ERROR 4xx: $body")
        val mensaje = extraerMensajeError(body, e.response.status.value)
        Result.failure(ApiException(e.response.status.value, mensaje))
    } catch (e: io.ktor.client.plugins.ServerResponseException) {
        val body = e.response.bodyAsText()
        println("API ERROR 5xx: $body")
        val mensaje = extraerMensajeError(body, e.response.status.value)
        Result.failure(ApiException(e.response.status.value, mensaje))
    } catch (e: Exception) {
        // Agrega este log
        println("EXCEPCION TIPO: ${e::class.simpleName} — ${e.message}")
        Result.failure(ApiException(0, "Sin conexión con el servidor"))
    }
}


private fun extraerMensajeError(body: String, codigo: Int): String {
    return try {
        val json = Json.parseToJsonElement(body).jsonObject
        println("JSON KEYS: ${json.keys}")  // ← temporal
        val mensaje = json["mensaje"]?.jsonPrimitive?.content
            ?: json["detail"]?.jsonPrimitive?.content
            ?: json["message"]?.jsonPrimitive?.content
            ?: json["title"]?.jsonPrimitive?.content
            ?: json["error"]?.jsonPrimitive?.content
            ?: "Error $codigo"

        when {
            codigo == 500 -> "Error al guardar. Verifica que la identificación o email no estén registrados."
            codigo == 409 -> "El registro ya existe."
            mensaje != null -> mensaje
            else -> "Error $codigo"
        }

    } catch (e: Exception) {
        println("ERROR PARSEANDO: ${e.message}")  // ← temporal
        if (body.isNotBlank()) body else "Error $codigo"
    }
}