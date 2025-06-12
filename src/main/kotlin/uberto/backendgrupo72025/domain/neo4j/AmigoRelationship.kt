package uberto.backendgrupo72025.domain.neo4j

import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship
import org.springframework.data.neo4j.core.schema.RelationshipProperties

@Node("Amistad")
data class AmigoRelationship(
    @Id @GeneratedValue
    val id: String? = "",
    @Relationship(type = "amigosA", direction = Relationship.Direction.OUTGOING)
    val amigoA: ViajeroNode,
    @Relationship(type = "amigosB", direction = Relationship.Direction.OUTGOING)
    val amigoB: ViajeroNode
)
