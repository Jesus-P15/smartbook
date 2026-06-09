package co.edu.cecar.smartbooks.data.network

import android.content.Context
import android.util.Base64
import co.edu.cecar.smartbooks.data.DataClass.perfil.PerfilResponse
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

object SessionManager {

    private var token: String = ""
    private var email: String = ""

    fun init(context: Context) {
        val prefs = context.getSharedPreferences("smartbooks", Context.MODE_PRIVATE)
        token = prefs.getString("token", "") ?: ""
        email = prefs.getString("email", "") ?: ""
    }

    fun guardarToken(context: Context, nuevoToken: String) {
        token = nuevoToken
        context.getSharedPreferences("smartbooks", Context.MODE_PRIVATE)
            .edit().putString("token", nuevoToken).apply()
    }

    fun obtenerToken(): String = token

    fun cerrarSesion(context: Context) {
        token = ""
        email=""
        context.getSharedPreferences("smartbooks", Context.MODE_PRIVATE)
            .edit().remove("token").apply()
    }

    fun estaAutenticado(): Boolean = token.isNotEmpty()




    fun obtenerPerfil(): PerfilResponse? {
        return try {
            val partes = token.split(".")
            if (partes.size != 3) return null
            val payload = Base64.decode(
                partes[1].padEnd((partes[1].length + 3) / 4 * 4, '='),
                Base64.URL_SAFE or Base64.NO_WRAP
            )
            val json = Json.parseToJsonElement(String(payload)).jsonObject
            PerfilResponse(
                id      = json["id"]!!.jsonPrimitive.content.toInt(),
                nombres = json["http://schemas.xmlsoap.org/ws/2005/05/identity/claims/name"]!!.jsonPrimitive.content,
                email   = email,
                rol     = json["http://schemas.microsoft.com/ws/2008/06/identity/claims/role"]!!.jsonPrimitive.content
            )
        } catch (e: Exception) {
            android.util.Log.e("JWT", "Error leyendo token: ${e.message}")
            null
        }
    }

    fun obtenerRol(): String {
        return try {
            val partes = token.split(".")
            if (partes.size != 3) return ""
            val payload = Base64.decode(
                partes[1].padEnd((partes[1].length + 3) / 4 * 4, '='),
                Base64.URL_SAFE or Base64.NO_WRAP
            )
            val json = Json.parseToJsonElement(String(payload)).jsonObject
            json["http://schemas.microsoft.com/ws/2008/06/identity/claims/role"]
                ?.jsonPrimitive?.content ?: ""
        } catch (e: Exception) { "" }
    }

}