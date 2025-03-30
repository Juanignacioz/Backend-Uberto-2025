package uberto.backendgrupo72025.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Component
import uberto.backendgrupo72025.domain.Viaje


import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import uberto.backendgrupo72025.domain.Comentario
import uberto.backendgrupo72025.domain.Vehiculo

@Repository
interface ViajeRepository  : CrudRepository<Viaje, Long> {

    @Query("SELECT v FROM Viaje v " +
            "where v.conductor.id = :id or v.viajero.id = :id")
    fun findByViajeroIdOrConductorId(id: Long): List<Viaje>

    @Query("SELECT v FROM Viaje v " +
            "WHERE v.conductor.id = :id OR v.viajero.id = :id " +
            "AND v.fechaFin > FUNCTION('DATE_TRUNC', 'second', CURRENT_TIMESTAMP)")
    fun findViajesPendientesByUsuarioId(id: Long): List<Viaje>

    @Query("SELECT v FROM Viaje v " +
            "WHERE v.conductor.id = :id OR v.viajero.id = :id " +
            "AND v.fechaFin < FUNCTION('DATE_TRUNC', 'second', CURRENT_TIMESTAMP)")
    fun findViajesRealizadosByUsuarioId(id: Long): List<Viaje>

    @Query("SELECT SUM(v.importe) FROM Viaje v " +
            "WHERE v.conductor.id = :id " +
            "AND v.fechaFin < FUNCTION('DATE_TRUNC', 'second', CURRENT_TIMESTAMP)")
    fun sumTotalFacturadoByChoferId(id: Long): Double

    @Query("""
    SELECT v FROM Viaje v 
    WHERE (v.conductor.id = :id)
    AND v.fechaFin > FUNCTION('DATE_TRUNC', 'second', CURRENT_TIMESTAMP) 
    AND (:usernameViajero IS NULL OR :usernameViajero = '' OR LOWER(v.viajero.username) LIKE LOWER(CONCAT('%', :usernameViajero, '%')))
    AND (:origen IS NULL OR :origen = '' OR LOWER(v.origen) LIKE LOWER(CONCAT('%', :origen, '%')))
    AND (:destino IS NULL OR :destino = '' OR LOWER(v.destino) LIKE LOWER(CONCAT('%', :destino, '%')))
    AND (:cantidadDePasajeros IS NULL OR :cantidadDePasajeros = 0 OR v.cantidadDePasajeros = :cantidadDePasajeros)
""")
     fun findViajesFiltradosByConductorId(
        id: Long,
        usernameViajero: String?,
        origen: String?,
        destino: String?,
        cantidadDePasajeros: Int?
    ): List<Viaje>
}
