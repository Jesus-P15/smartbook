package co.edu.cecar.smartbooks.data.DataClass.Lotes

import kotlinx.serialization.Serializable

@Serializable
data class CrearLoteRequest(
    val lote: Int = 0
)