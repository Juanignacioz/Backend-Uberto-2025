package uberto.backendgrupo72025.repository


import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import uberto.backendgrupo72025.domain.Comentario

@Repository
interface ComentarioRepository  : CrudRepository<Comentario, String?> {

    fun findByViajeViajeroIdAndActive(id: String?, active: Boolean = true): List<Comentario>

    fun findByViajeConductorIdAndActive(conductorId: String?, active: Boolean = true): List<Comentario>

    @Query("SELECT COALESCE(ROUND(AVG(c.estrellas), 2), 0.0) FROM Comentario c " +
            "INNER JOIN Viaje v on v.id = c.viaje.id " +
            "where v.conductor.id = :idConductor and c.active = true")
    fun promedioEstrellasByConductor(idConductor: String?): Double

    fun existsByViajeIdAndActive(viajeId: String?, active: Boolean): Boolean

}

