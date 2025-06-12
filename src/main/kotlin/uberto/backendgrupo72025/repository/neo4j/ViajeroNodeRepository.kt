package uberto.backendgrupo72025.repository.neo4j

import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.neo4j.repository.query.Query
import org.springframework.stereotype.Repository
import uberto.backendgrupo72025.domain.neo4j.ViajeroNode

@Repository
interface ViajeroNodeRepository: Neo4jRepository<ViajeroNode, String> {

    @Query("""
        MATCH (viajero:Viajero {viajeroId:$0}),(amigo:Viajero {viajeroId:$1}) 
        CREATE (viajero)-[:AMISTAD]->(amigo)
    """)
    fun crearAmistad(viajeroId:String?, amigoId: String?)


    @Query("""
        MATCH (v:Viajero {viajeroId:$0})-[:AMISTAD]->(amigo:Viajero)
        RETURN amigo
    """)
    fun findAmigosDirectos(viajeroId: String): List<ViajeroNode>

}