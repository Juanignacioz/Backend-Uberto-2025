package uberto.backendgrupo72025.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import uberto.backendgrupo72025.dto.*
import uberto.backendgrupo72025.domain.Conductor
import uberto.backendgrupo72025.domain.NotFoundException
import uberto.backendgrupo72025.domain.Viaje
import uberto.backendgrupo72025.domain.Viajero
import uberto.backendgrupo72025.repository.jpa.ViajeRepository
import uberto.backendgrupo72025.repository.mongo.ConductorRepository
import uberto.backendgrupo72025.security.TokenUtils

@Service
class ViajeService(
    val viajeRepository: ViajeRepository,
    val comentarioService: ComentarioService
) {

    @Autowired
    private lateinit var conductorRepository: ConductorRepository

    @Autowired
    lateinit var tokenUtils: TokenUtils

    fun getAllViajes() = viajeRepository.findAll()

    fun getViajeById(idViaje: String?) = idViaje?.let {
        viajeRepository.findById(it).orElseThrow { NotFoundException("No se encontró el viaje con id $idViaje") }
    }!!

    fun getViajesByUsuarioId(idUsuario: String?) = viajeRepository.findByViajeroIdOrConductorId(idUsuario)

    fun crearViaje(viajeDTO: ViajeDTO, viajero: Viajero, conductor: Conductor): Viaje {
        val viaje = viajeDTO.toViaje(viajero, conductor)
        viajeRepository.save(viaje)
        return viaje
    }

    fun getViajesRealizadosByUsuario(bearerToken: String): ViajesCompletadosDTO {
        val (userID, esChofer) = tokenUtils.authenticate(bearerToken)

        lateinit var viajesRealizadosDTO: List<ViajeDTO>
        lateinit var viajesRealizados: List<Viaje>
        var totalFacturado = 0.0

        if (esChofer) {
            viajesRealizados = viajeRepository.findByConductorIdAndFechaFinBefore(userID)
            viajesRealizadosDTO = viajesRealizados.map {
                it.toViajeDTO(
                    it.viajero.nombreYApellido(),
                    it.viajero.foto,
                    viajeCalificable(it)
                )
            }
            totalFacturado = getTotalFacturado(userID)
        } else {
            viajesRealizados = viajeRepository.findByViajeroIdAndFechaFinBefore(userID)

            viajesRealizadosDTO = viajesRealizados.map {
                val conductor = conductorRepository.findById(it.conductorId!!).get()
                it.toViajeDTO(
                    conductor.nombreYApellido(),
                    conductor.foto,
                    viajeCalificable(it)
                )
            }
        }
        return ViajesCompletadosDTO(viajesRealizadosDTO, totalFacturado)
    }

    fun getTotalFacturado(idUsuario: String?) = viajeRepository.sumTotalFacturadoByChoferId(idUsuario)

    fun viajeCalificable(viaje: Viaje) = !viaje.viajePendiente() && !viaje.viajeComentado

    fun getViajesPendientesByUsuario(bearerToken: String): List<ViajeDTO> {
        val (userID, esChofer) = tokenUtils.authenticate(bearerToken)

        lateinit var viajesPendientes: List<Viaje>
        if (esChofer) {
            viajesPendientes = viajeRepository.findByConductorIdAndFechaFinAfter(userID)
            return viajesPendientes.map {
                it.toViajeDTO(
                    it.viajero.nombreYApellido(),
                    it.viajero.foto,
                    viajeCalificable(it)
                )
            }
        } else {
            viajesPendientes = viajeRepository.findByViajeroIdAndFechaFinAfter(userID)
            return viajesPendientes.map {
                val conductor = conductorRepository.findById(it.conductorId!!).get()
                it.toViajeDTO(
                    conductor.nombreYApellido(),
                    conductor.foto,
                    viajeCalificable(it)
                )
            }
        }
    }

    fun getViajesConductorFiltrados(bearerToken: String, filtroDTO: FiltroDTO): List<ViajeDTO> {
        val (userID, esChofer) = tokenUtils.authenticate(bearerToken)
        return viajeRepository.findViajesFiltradosByConductorId(
            userID,
            filtroDTO.usernameViajero, filtroDTO.origen, filtroDTO.destino, filtroDTO.cantidadDePasajeros
        )
            .map { it.toViajeDTO(it.viajero.nombreYApellido(), it.viajero.foto, viajeCalificable(it)) }
    }

    fun save(viaje: Viaje) {
        viajeRepository.save(viaje)
    }
}