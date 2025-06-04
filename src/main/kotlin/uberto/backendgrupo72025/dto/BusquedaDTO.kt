package uberto.backendgrupo72025.dto

import uberto.backendgrupo72025.domain.UltimaBusqueda
import java.time.LocalDateTime

data class BusquedaDTO (
    val origen: String,
    val destino: String,
    val fecha: String,
    val duracion: Int,
    val cantidadDePasajeros: Int
)

fun BusquedaDTO.toUltimaBusqueda(idViajero: String, fecha: LocalDateTime) = UltimaBusqueda(
    viajeroId = idViajero,
    origen = origen,
    destino = destino,
    fecha =  fecha,
    cantidadDePasajeros = cantidadDePasajeros
)
