package uberto.backendgrupo72025.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import uberto.backendgrupo72025.domain.NotFoundException
import uberto.backendgrupo72025.domain.neo4j.ConductorNode
import uberto.backendgrupo72025.domain.neo4j.ViajeRelation
import uberto.backendgrupo72025.domain.neo4j.ViajeroNode
import uberto.backendgrupo72025.dto.AmigoDTO
import uberto.backendgrupo72025.dto.toAmigoDTO
import uberto.backendgrupo72025.repository.neo4j.ConductorNodeRepository
import uberto.backendgrupo72025.repository.neo4j.ViajeroNodeRepository
import uberto.backendgrupo72025.security.TokenUtils
import java.time.LocalDateTime

@Service
class Neo4jService(
    val viajeroNodeRepository: ViajeroNodeRepository,
    val conductorNodeRepository: ConductorNodeRepository,
) {
    @Autowired
    lateinit var tokenUtils: TokenUtils

    fun getViajeroNodeById(idViajero: String?): ViajeroNode = viajeroNodeRepository.findByViajeroId(idViajero)
        .orElseThrow { NotFoundException("No se encontro el nodo del viajero solicitado") }

    fun getConductorNodeById(idConductor: String?): ConductorNode =
        conductorNodeRepository.findByConductorId(idConductor)
            .orElseThrow { NotFoundException("No se encontro el nodo del conductor solicitado") }

    @Transactional("neo4jTransactionManager")
    fun agregarAmigo(bearerToken: String, idAmigo: String?): AmigoDTO {
        val (userID, esChofer) = tokenUtils.decodificatorAuth(bearerToken)
        val viajero = getViajeroNodeById(userID)
        val nuevoAmigo = getViajeroNodeById(idAmigo)
        viajero.agregarAmigo(nuevoAmigo)
        viajeroNodeRepository.save(viajero)
        return nuevoAmigo.toAmigoDTO()
    }

    fun getAmigos(bearerToken: String): List<AmigoDTO> {
        val (userID, esChofer) = tokenUtils.decodificatorAuth(bearerToken)
        val viajero = getViajeroNodeById(userID)
        return viajero.amigos.map { it.toAmigoDTO() }
    }

    @Transactional("neo4jTransactionManager")
    fun crearViaje(idViajero: String, idConductor: String?, fechafin: LocalDateTime) {
        val viajeroNode = getViajeroNodeById(idViajero)
        val conductorNode = getConductorNodeById(idConductor)
        viajeroNode.agregarViaje(ViajeRelation(conductorNode, fechafin))
        viajeroNodeRepository.save(viajeroNode)
    }

}