package uberto.backendgrupo72025.domain.neo4j

import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.RelationshipProperties
import org.springframework.data.neo4j.core.schema.TargetNode
import java.time.LocalDateTime

@RelationshipProperties
data class ViajeRelation(
    @Id
    @GeneratedValue
    var id: String? = null,
    @TargetNode
    var conductor: ConductorNode,
    val fechaDeFinalizacion: LocalDateTime
) {
    constructor(conductorNode: ConductorNode, fechaFin: LocalDateTime) : this(
        conductor = conductorNode,
        fechaDeFinalizacion = fechaFin
    )
}