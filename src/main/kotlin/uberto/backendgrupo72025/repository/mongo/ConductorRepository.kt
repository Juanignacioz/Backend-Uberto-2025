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
    @Aggregation(pipeline = [
        "{ \$addFields: { idConductorStr: { \$toString: \"\$_id\" } } }",
        "{ \$lookup: { from: \"viaje_resumido\", let: { idConductor: \"\$idConductorStr\" }, pipeline: [ { \$match: { \$expr: { \$and: [ { \$eq: [ \"\$conductorId\", \"$\$idConductor\" ] }, { \$lt: [ \"\$fechaInicio\", ?1 ] }, { \$gte: [ \"\$fechaFin\", ?0 ] } ] } } } ], as: \"viajesSolapados\" } } ",
        "{ \$match: { viajesSolapados: { \$size: 0 } } }"
    ])
    fun findConductoresDisponibles(
        nuevaFechaInicio: LocalDateTime,
        nuevaFechaFin: LocalDateTime
    ): List<Conductor>


}



