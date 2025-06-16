package uberto.backendgrupo72025.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import uberto.backendgrupo72025.domain.NotFoundException
import uberto.backendgrupo72025.repository.neo4j.ConductorNodeRepository
import uberto.backendgrupo72025.repository.neo4j.ViajeroNodeRepository
import java.time.LocalDateTime

@Service
class Neo4jService(
    val viajeroNodeRepository: ViajeroNodeRepository,
    val conductorNodeRepository: ConductorNodeRepository,
) {

    @Transactional("neo4jTransactionManager")
    fun crearViaje(idViajero: String, idConductor: String?, fechafin: LocalDateTime) {
        val viajero = viajeroNodeRepository.findByViajeroId(idViajero)
            .orElseThrow { NotFoundException("No se encontro el nodo del viajero solicitado") }
        val conductor = conductorNodeRepository.findByConductorId(idConductor!!)
            .orElseThrow { NotFoundException("No se encontro el nodo del conductor solicitado") }
        viajeroNodeRepository.crearRelacionViaje(idViajero,idConductor,fechafin)
//        val nuevoViaje = ViajeRelation(
//            conductor = conductor,
//            fechaDeFinalizacion = fechafin
//        )
//        viajero.agregarViaje(nuevoViaje)
  //      println("viajesssssssssss"+ viajero.viajes)
//        viajeroNodeRepository.save(viajero)
    }

}