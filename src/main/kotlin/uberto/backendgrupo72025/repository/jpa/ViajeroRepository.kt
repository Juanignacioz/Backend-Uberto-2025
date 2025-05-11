package uberto.backendgrupo72025.repository.jpa

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import uberto.backendgrupo72025.domain.Viajero

@Repository
interface ViajeroRepository: CrudRepository<Viajero, String?> {

    fun findByUsernameAndContrasenia(username: String, contrasenia: String): Viajero?

    @EntityGraph(attributePaths = ["amigos"])
    fun findViajeroPerfilById(id: String?): Viajero

    @Query("""
    SELECT v FROM Viajero v 
    WHERE v.id != :id
    AND v.id NOT IN (
        SELECT a.id FROM Viajero u JOIN u.amigos a WHERE u.id = :id
    )
    AND (
        LOWER(CONCAT(v.nombre, ' ', v.apellido)) LIKE LOWER(CONCAT('%', :query, '%')) 
        OR LOWER(v.username) LIKE LOWER(CONCAT('%', :query, '%'))
    )
""")
    fun buscarViajerosNoAmigos(id: String?, query: String): List<Viajero>

}