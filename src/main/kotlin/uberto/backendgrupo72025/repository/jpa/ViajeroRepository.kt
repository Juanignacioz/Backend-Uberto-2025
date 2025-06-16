package uberto.backendgrupo72025.repository.jpa

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import uberto.backendgrupo72025.domain.Viajero

@Repository
interface ViajeroRepository: CrudRepository<Viajero, String?> {

    fun findByUsernameAndContrasenia(username: String, contrasenia: String): Viajero?

    fun findViajeroPerfilById(id: String?): Viajero

}