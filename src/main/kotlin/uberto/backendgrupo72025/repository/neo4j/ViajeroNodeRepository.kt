package uberto.backendgrupo72025.repository.neo4j

import io.jsonwebtoken.security.Jwks.OP
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.neo4j.repository.query.Query
import org.springframework.stereotype.Repository
import uberto.backendgrupo72025.domain.neo4j.ViajeroNode
import java.time.LocalDateTime
import java.util.Optional

@Repository
interface ViajeroNodeRepository : Neo4jRepository<ViajeroNode, String> {

    fun findByViajeroId(viajeroId: String?): Optional<ViajeroNode>

    @Query(
        """
         MATCH (v:Viajero {viajeroId:$0})-[a:AMISTAD]->(amigo:Viajero {viajeroId: $1})
         DELETE a
    """
    )
    fun eliminarAmigoRelation(viajeroId: String?, amigoId: String?)

    @Query(
        """
    MATCH (v:Viajero)
    WHERE (v.nombre_y_apellido =~ $1 OR v.username =~ $1)
      AND v.viajeroId <> $0
      AND NOT EXISTS {
          MATCH (:Viajero {viajeroId: $0})-[:AMISTAD]->(v)
      }
    RETURN v
    """
    )
    fun buscarViajerosNoAmigosRegex(
        viajeroId: String,
        regex: String
    ): List<ViajeroNode>


    @Query(
        """
    MATCH (v:Viajero {viajeroId:$0})-[:AMISTAD*2]->(amigoDeAmigo:Viajero)
    WHERE NOT (v)-[:AMISTAD]->(amigoDeAmigo)
    AND amigoDeAmigo.viajeroId <> v.viajeroId
    WITH DISTINCT amigoDeAmigo, v
    MATCH (v)-[r1:VIAJO_CON]->(chofer:Chofer)<-[r2:VIAJO_CON]-(amigoDeAmigo)
    WHERE r1.fechaDeFinalizacion < localdatetime() AND r2.fechaDeFinalizacion < localdatetime()
    RETURN DISTINCT amigoDeAmigo
    """
    )
    fun findAmigosDeAmigosConMismoConductor(viajeroId: String): List<ViajeroNode>


}