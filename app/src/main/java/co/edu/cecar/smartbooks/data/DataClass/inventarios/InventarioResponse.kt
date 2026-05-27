package co.edu.cecar.smartbooks.data.DataClass.inventarios

import kotlinx.serialization.Serializable

@Serializable
data class InventarioResponse(
    val nivelLibro: String,
    val nombreLibro: String,
    val edicionLibro: String,
    val tipoLibro: String,
    val cantidadIngresada: Int,
    val cantidadVendida: Int,
    val stockDisponible: Int,
    val lote: Int
)