package uberto.backendgrupo72025.repository.neo4j

import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.stereotype.Repository
import uberto.backendgrupo72025.domain.neo4j.ConductorNode
import java.util.*

@Repository
interface ConductorNodeRepository: Neo4jRepository<ConductorNode, String> {

    fun findByConductorId(conductorId: String?): Optional<ConductorNode>
}
