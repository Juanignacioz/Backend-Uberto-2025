package uberto.backendgrupo72025.repository.neo4j

import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.stereotype.Repository
import uberto.backendgrupo72025.domain.Amigo

@Repository
interface AmigoRepository: Neo4jRepository<Amigo, String> {

}