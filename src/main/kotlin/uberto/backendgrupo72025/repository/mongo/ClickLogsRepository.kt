package uberto.backendgrupo72025.repository.mongo

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
 import uberto.backendgrupo72025.ClickLogs

@Repository
interface ClickLogRepository : MongoRepository<ClickLogs, String> {

  fun countByConductorId(conductorId: String): Int

  fun findByconductorId(conductorId: String): Int


}