package uberto.backendgrupo72025.repository.mongo

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository
import uberto.backendgrupo72025.domain.Conductor
import java.time.LocalDateTime

@Repository
interface ConductorRepository : MongoRepository<Conductor, String> {

    fun findByUsernameAndContrasenia(username: String, contrasenia: String): Conductor?

    @Query(
        "{ " +
        "\$or: [ " +
        "  /* Caso 1: No tiene campo viajes */ " +
        "  { viajes: { \$exists: false } }, " +
        "  /* Caso 2: Tiene viajes pero la lista está vacía */ " +
        "  { viajes: { \$size: 0 } }, " +
        "  /* Caso 3: Tiene viajes pero ninguno solapa y no está en estados no disponibles */ " +
        "  { " +
        "    viajes: { " +
        "      \$not: { " +
        "        \$elemMatch: { " +
        "          \$and: [ " +
        "            /* Solapamiento temporal */ " +
        "            { 'fechaInicio': { \$lt: ?1 } }, " +
        "            { 'fechaFin': { \$gt: ?0 } }, " +
        "            /* Exclusión de estados */ " +
        "            { 'estado': { \$nin: ['REALIZADO', 'EN_CURSO', 'CONFIRMADO'] } } " +
        "          ] " +
        "        } " +
        "      } " +
        "    } " +
        "  } " +
        "] " +
        "}"
    )
    fun findConductoresDisponibles(
        nuevaFechaInicio: LocalDateTime,
        nuevaFechaFin: LocalDateTime
    ): List<Conductor>

}


// fun findConductorById
//    @Query("""
//    SELECT c
//    FROM Conductor c
//    WHERE NOT EXISTS (
//        SELECT 1 FROM Viaje v
//        WHERE v.conductor.id = c.id
//        AND (
//            (v.fechaInicio < :nuevaFechaFin AND v.fechaFin > :nuevaFechaInicio)
//            OR (v.fechaInicio < :nuevaFechaInicio AND v.fechaFin > :nuevaFechaInicio)
//        )
//    )
//""")
//    fun findConductoresDisponibles(nuevaFechaInicio: LocalDateTime, nuevaFechaFin: LocalDateTime): List<Conductor>

