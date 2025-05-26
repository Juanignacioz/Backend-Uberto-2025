package uberto.backendgrupo72025.repository.mongo

import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository
import uberto.backendgrupo72025.domain.Conductor
import uberto.backendgrupo72025.domain.DataViaje
import uberto.backendgrupo72025.dto.ConductorDTO
import java.time.LocalDateTime

@Repository
interface ConductorRepository : MongoRepository<Conductor, String> {

    fun findByUsernameAndContrasenia(username: String, contrasenia: String): Conductor?

    //fun findByIdIn(ids: List<String>): List<Conductor>
    @Aggregation(
        "{ '\$lookup': { " +
                "    'from': 'viaje_resumido', " +
                "    'let': { 'conductorId': '\$_id' }, " +
                "    'pipeline': [ " +
                "        { " +
                "            '\$match': { " +
                "                '\$expr': { " +
                "                    '\$and': [ " +
                "                        { '\$eq': [ '\$conductorId', '$\$conductorId' ] }, " +
                "                        { '\$lt': [ '\$fechaInicio', ?0 ] }, " +
                "                        { '\$gt': [ '\$fechaFin', ?1 ] } " +
                "                    ] " +
                "                } " +
                "            } " +
                "        } " +
                "    ], " +
                "    'as': 'viajesSuperpuestos' " +
                "} }",
        "{ '\$match':  { \"viajesSuperpuestos.0\": { '\$exists': false } }}"
    )
    fun findConductoresDisponibles(
        fechaInicio: LocalDateTime,  // ?0
        fechaFin: LocalDateTime      // ?1
    ): List<Conductor>


}



