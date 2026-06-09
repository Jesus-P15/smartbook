package co.edu.cecar.smartbooks.data.Constants.controlError

import kotlinx.serialization.Serializable

@Serializable
data class ApiException(
    val codigo: Int,
    override val message: String
) : Exception(message)