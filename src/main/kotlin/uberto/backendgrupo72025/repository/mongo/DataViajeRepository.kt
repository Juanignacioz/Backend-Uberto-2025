package uberto.backendgrupo72025.repository.mongo

import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import uberto.backendgrupo72025.domain.Conductor
import uberto.backendgrupo72025.domain.DataViaje
import java.time.LocalDateTime

@Repository
interface DataViajeRepository : MongoRepository<DataViaje, String> {

}


