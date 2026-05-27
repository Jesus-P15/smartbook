package co.edu.cecar.smartbooks.data.network

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object SessionManager {

    private var token: String = ""

    fun init(context: Context) {
        val prefs = context.getSharedPreferences("smartbooks", Context.MODE_PRIVATE)
        token = prefs.getString("token", "") ?: ""
    }

    fun guardarToken(context: Context, nuevoToken: String) {
        token = nuevoToken
        context.getSharedPreferences("smartbooks", Context.MODE_PRIVATE)
            .edit().putString("token", nuevoToken).apply()
    }

    fun obtenerToken(): String = token

    fun cerrarSesion(context: Context) {
        token = ""
        context.getSharedPreferences("smartbooks", Context.MODE_PRIVATE)
            .edit().remove("token").apply()
    }

    fun estaAutenticado(): Boolean = token.isNotEmpty()
}