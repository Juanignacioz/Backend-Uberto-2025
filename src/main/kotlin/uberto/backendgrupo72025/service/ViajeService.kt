package uberto.backendgrupo72025.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import uberto.backendgrupo72025.domain.*
import uberto.backendgrupo72025.dto.*
import uberto.backendgrupo72025.repository.jpa.ViajeRepository
import uberto.backendgrupo72025.repository.mongo.ConductorRepository
import uberto.backendgrupo72025.repository.mongo.DataViajeRepository
import uberto.backendgrupo72025.security.TokenUtils
import java.time.LocalDateTime

@Service
class ViajeService(
    val viajeRepository: ViajeRepository,
    val dataViajeRepository: DataViajeRepository
) {

    @Autowired
    lateinit var tokenUtils: TokenUtils

    fun getAllViajes() = viajeRepository.findAll()

    fun getViajeById(idViaje: String?) = idViaje?.let {
        viajeRepository.findById(it).orElseThrow { NotFoundException("No se encontró el viaje con id $idViaje") }
    }!!

    fun getViajesByUsuarioId(idUsuario: String?) = viajeRepository.findByViajeroIdOrConductorId(idUsuario)

    fun crearViaje(viajeDTO: ViajeDTO, viajero: Viajero, conductor: Conductor): Viaje {
        val viaje = viajeRepository.save( viajeDTO.toViaje(viajero, conductor))
        dataViajeRepository.save(
            DataViaje(
                id = viaje.id,
                conductorId = viaje.conductorId,
                fechaInicio = viaje.fechaInicio,
                fechaFin = viaje.fechaFin
            )
        )
        return viaje
    }

    fun cancelarViaje(viajeId: String) {
        dataViajeRepository.deleteById(viajeId)
    }

    fun getViajesRealizadosByConductor(userID: String?): List<ViajeDTO> {
        return viajeRepository.findByConductorIdAndFechaFinBefore(userID).map {
            it.toViajeDTO(
                it.viajero.nombreYApellido(),
                it.viajero.foto,
                viajeCalificable(it)
            )
        }
    }

    fun getViajesRealizadosByViajero(userID: String?) = viajeRepository.findByViajeroIdAndFechaFinBefore(userID)

    fun getTotalFacturado(idUsuario: String?) = viajeRepository.sumTotalFacturadoByChoferId(idUsuario)

    fun viajeCalificable(viaje: Viaje) = !viaje.viajePendiente() && !viaje.viajeComentado

    fun getViajesPendientesByConductor(userID: String?): List<ViajeDTO> {
        return viajeRepository.findByConductorIdAndFechaFinAfter(userID).map {
            it.toViajeDTO(
                it.viajero.nombreYApellido(),
                it.viajero.foto,
                viajeCalificable(it)
            )
        }
    }

    fun getViajesPendientesByViajero(userID: String?) = viajeRepository.findByViajeroIdAndFechaFinAfter(userID)

    fun getViajesConductorFiltrados(bearerToken: String, filtroDTO: FiltroDTO): List<ViajeDTO> {
        val (userID, esChofer) = tokenUtils.decodificatorAuth(bearerToken)
        return viajeRepository.findViajesFiltradosByConductorId(
            userID,
            filtroDTO.usernameViajero, filtroDTO.origen, filtroDTO.destino, filtroDTO.cantidadDePasajeros
        )
            .map { it.toViajeDTO(it.viajero.nombreYApellido(), it.viajero.foto, viajeCalificable(it)) }
    }

    fun save(viaje: Viaje) {
        viajeRepository.save(viaje)

    }

    fun getConductoresDisponibles(nuevaFecha: LocalDateTime, nuevaFechaFin: LocalDateTime) =
        viajeRepository.findConductoresDisponibles(nuevaFecha, nuevaFechaFin)
}