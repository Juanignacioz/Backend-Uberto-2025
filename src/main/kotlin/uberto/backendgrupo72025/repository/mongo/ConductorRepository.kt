package uberto.backendgrupo72025.repository.mongo

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import uberto.backendgrupo72025.domain.Conductor

@Repository
interface ConductorRepository: MongoRepository<Conductor, String?> {

    fun findByUsernameAndContrasenia(username: String, contrasenia: String): List<Conductor?>

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
}