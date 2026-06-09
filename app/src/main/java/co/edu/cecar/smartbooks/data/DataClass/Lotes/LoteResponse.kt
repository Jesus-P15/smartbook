package co.edu.cecar.smartbooks.data.DataClass.Lotes

import kotlinx.serialization.Serializable

@Serializable
data class LoteResponse(
    val codigo: Int,
    val actual: Boolean? = null
)