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
        RETURN amigo
    """)
    fun crearAmistad(viajeroId:String?, amigoId: String?): ViajeroNode

    @Query("""
        MATCH (v:Viajero {viajeroId:$0})-[:AMISTAD]->(amigo:Viajero)
        RETURN amigo
    """)
    fun findAmigosDirectos(viajeroId: String): List<ViajeroNode>

    @Query("""
         MATCH (v:Viajero {viajeroId:$0})-[a:AMISTAD]->(amigo:Viajero {viajeroId: $1})
         DELETE a
    """)
    fun eliminarAmigoRelation(viajeroId: String?, amigoId: String?)

    @Query("""
    MATCH (v:Viajero)
    WHERE (v.nombre_y_apellido =~ $1 OR v.username =~ $1)
      AND v.viajeroId <> $0
      AND NOT EXISTS {
          MATCH (:Viajero {viajeroId: $0})-[:AMISTAD]->(v)
      }
    RETURN v
    """)
    fun buscarViajerosNoAmigosRegex(
        viajeroId: String,
        regex: String
    ): List<ViajeroNode>

//    @Query("""
//    MATCH (v:Viajero {viajeroId:$0})-[:AMISTAD*2]->(amigoDeAmigo:Viajero)
//    WHERE NOT (v)-[:AMISTAD]->(amigoDeAmigo)
//    RETURN DISTINCT amigoDeAmigo
//    """)
//    fun findAmigosDeAmigos(viajeroId: String): List<ViajeroNode>

    @Query("""
    MATCH (v:Viajero {viajeroId:$0})-[:AMISTAD*2]->(amigoDeAmigo:Viajero)
    WHERE NOT (v)-[:AMISTAD]->(amigoDeAmigo)
    AND amigoDeAmigo.idUsuario <> usuario.idUsuario
    WITH DISTINCT amigoDeAmigo, v
    MATCH (v)-[:VIAJO_CON]->(conductor:Conductor)<-[:VIAJO_CON]-(amigoDeAmigo)
    RETURN DISTINCT amigoDeAmigo
    """)
    fun findAmigosDeAmigosConMismoConductor(viajeroId: String): List<ViajeroNode>

}