package uberto.backendgrupo72025.repository.mongo

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository
import uberto.backendgrupo72025.domain.Conductor
import java.time.LocalDateTime

@Repository
interface ConductorRepository : MongoRepository<Conductor, String> {

    fun findByUsernameAndContrasenia(username: String, contrasenia: String): Conductor?

    fun findByIdIn(ids: List<String>): List<Conductor>
}



