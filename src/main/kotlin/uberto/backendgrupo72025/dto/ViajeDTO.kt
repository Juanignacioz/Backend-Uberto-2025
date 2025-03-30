package uberto.backendgrupo72025.dto

import uberto.backendgrupo72025.domain.Conductor
import uberto.backendgrupo72025.domain.Viaje
import uberto.backendgrupo72025.domain.Viajero
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit


data class ViajeDTO(
    val id: Long,
    val idViajero: Long,
    val idConductor: Long,
    var nombre: String,
    val origen: String,
    val destino: String,
    val fechaInicio: String,
    val cantidadDePasajeros: Int,
    val duracion: Int,
    val importe: Double,
    val puedeCalificar: Boolean,
    val fechaFin: String,
    val foto : String?
)
data class ViajesCompletadosDTO(
    val viajesRealizados: List<ViajeDTO>,
    val totalFacturado: Double
)


fun ViajeDTO.toViaje(viajero: Viajero, conductor: Conductor) = Viaje(
    viajero = viajero,
    conductor = conductor,
    origen = origen,
    destino =  destino,
    fechaInicio = LocalDateTime.parse(fechaInicio, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
    fechaFin = LocalDateTime.parse(fechaInicio, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")).plusMinutes(duracion.toLong()),
    cantidadDePasajeros = cantidadDePasajeros,
    duracion = duracion,
    importe = importe
)

fun Viaje.toViajeDTO(nombre: String, foto : String, puedeCalificar: Boolean) = ViajeDTO(
    id = id,
    idViajero = viajero.id,
    idConductor = conductor.id,
    nombre = nombre,
    origen = origen,
    destino =  destino,
    fechaInicio = fechaInicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
    cantidadDePasajeros = cantidadDePasajeros,
    duracion = duracion,
    importe = importe,
    puedeCalificar = puedeCalificar,
    fechaFin = fechaFin.format(DateTimeFormatter.ofPattern("HH:mm")),
    foto = foto
)