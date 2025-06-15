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
        MATCH (viajero:Viajero {viajeroId:$0}),(amigo:Viajero {viajeroId:$1}) 
        MERGE (viajero)-[:AMISTAD]->(amigo)
        RETURN amigo
    """
    )
    fun crearAmistad(viajeroId: String?, amigoId: String?): ViajeroNode

    @Query(
        """
        MATCH (v:Viajero {viajeroId:$0})-[:AMISTAD]->(amigo:Viajero)
        RETURN amigo
    """
    )
    fun findAmigosDirectos(viajeroId: String): List<ViajeroNode>

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
    ) //falta filtrar por fecha (viajes finalizados)
    fun findAmigosDeAmigosConMismoConductor(viajeroId: String): List<ViajeroNode>
//armar el circuito
    /*
    *   @Query("""
        MATCH (v:Viajero {viajeroId: \$viajeroId})-[:AMISTAD*2]->(amigoDeAmigo:Viajero)
        WHERE NOT (v)-[:AMISTAD]->(amigoDeAmigo)
        AND amigoDeAmigo.viajeroId <> v.viajeroId
        WITH DISTINCT amigoDeAmigo, v
        MATCH (v)-[:VIAJO_CON]->(viaje:Viaje)-[:VIAJO_CON]->(conductor:Conductor)
        MATCH (amigoDeAmigo)-[:VIAJO_CON]->(otroViaje:Viaje)-[:VIAJO_CON]->(conductor)
        RETURN DISTINCT amigoDeAmigo
    """)
    */

    @Query(
        """
        MATCH (v:Viajero {viajeroId: $0})
        MATCH (c:Chofer {conductorId: $1})
        CREATE (v)-[r:VIAJO_CON {
            fechaDeFinalizacion: $2
        }]->(c)
    """
    )
    fun crearRelacionViaje(
        viajeroId: String?,
        conductorId: String?,
        fechaFin: LocalDateTime
    )

}