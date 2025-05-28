package uberto.backendgrupo72025.repository.jpa

import org.springframework.data.jpa.repository.Query
import uberto.backendgrupo72025.domain.Viaje


import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface ViajeRepository  : CrudRepository<Viaje, String?> {

    @Query("SELECT v FROM Viaje v " +
            "where v.conductorId = :id or v.viajero.id = :id")
    fun findByViajeroIdOrConductorId(id: String?): List<Viaje>

    fun findByViajeroIdAndFechaFinAfter(idViajero: String?, fechaFin: LocalDateTime = LocalDateTime.now()): List<Viaje>

    fun findByConductorIdAndFechaFinAfter(idConductor: String?, fechaFin: LocalDateTime = LocalDateTime.now()): List<Viaje>

    fun findByViajeroIdAndFechaFinBefore(idViajero: String?, fechaFin: LocalDateTime = LocalDateTime.now()): List<Viaje>

    fun findByConductorIdAndFechaFinBefore(idConductor: String?, fechaFin: LocalDateTime = LocalDateTime.now()): List<Viaje>


    @Query("SELECT SUM(v.importe) FROM Viaje v " +
            "WHERE v.conductorId = :id " +
            "AND v.fechaFin < CURRENT_TIMESTAMP")
    fun sumTotalFacturadoByChoferId(id: String?): Double

    @Query("""
    SELECT v FROM Viaje v 
    WHERE (v.conductorId = :id)
    AND v.fechaFin > CURRENT_TIMESTAMP
    AND (:usernameViajero IS NULL OR :usernameViajero = '' OR LOWER(v.viajero.username) LIKE LOWER(CONCAT('%', :usernameViajero, '%')))
    AND (:origen IS NULL OR :origen = '' OR LOWER(v.origen) LIKE LOWER(CONCAT('%', :origen, '%')))
    AND (:destino IS NULL OR :destino = '' OR LOWER(v.destino) LIKE LOWER(CONCAT('%', :destino, '%')))
    AND (:cantidadDePasajeros IS NULL OR :cantidadDePasajeros = 0 OR v.cantidadDePasajeros = :cantidadDePasajeros)
""")
     fun findViajesFiltradosByConductorId(
        id: String?,
        usernameViajero: String?,
        origen: String?,
        destino: String?,
        cantidadDePasajeros: Int?
    ): List<Viaje>

    @Query("""
    SELECT DISTINCT v1.conductorId
    FROM Viaje v1
    WHERE v1.conductorId IS NOT NULL
    AND NOT EXISTS (
        SELECT 1 FROM Viaje v2
        WHERE v2.conductorId = v1.conductorId
        AND (
            (v2.fechaInicio < :nuevaFechaFin AND v2.fechaFin > :nuevaFechaInicio)
            OR (v2.fechaInicio < :nuevaFechaInicio AND v2.fechaFin > :nuevaFechaInicio)
        )
    )
""")
    fun findConductoresDisponibles(nuevaFechaInicio: LocalDateTime, nuevaFechaFin: LocalDateTime): List<String>
}
