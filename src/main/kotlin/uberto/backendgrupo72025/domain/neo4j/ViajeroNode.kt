package uberto.backendgrupo72025.domain.neo4j

import org.springframework.data.neo4j.core.schema.*
import org.springframework.data.neo4j.core.schema.Relationship.Direction
import uberto.backendgrupo72025.domain.Viajero

@Node("Viajero")
data class ViajeroNode(
    @Id
    @GeneratedValue
    val id: String = "",
    @Relationship(type = "VIAJO_CON", direction = Direction.OUTGOING)
    var viajoCon: MutableList<ConductorNode> = mutableListOf(),
    @Property("nombre_y_apellido")
    val nombreYApellido: String = "",
    val foto :String = "",
    val username :String = "",
    val viajeroId: String? = "",
) {
    constructor(viajero: Viajero) : this(
        viajeroId = viajero.id,
        nombreYApellido = viajero.nombreYApellido(),
        foto = viajero.foto,
        username = viajero.username
    )
}







@RelationshipProperties
data class Viajes(
    @Id @GeneratedValue
    var id: String? = null,
    @TargetNode
    var chofer: ConductorNode? = null
)