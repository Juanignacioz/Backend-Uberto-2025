package uberto.backendgrupo72025.domain

import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Property
import org.springframework.data.neo4j.core.schema.Relationship
import org.springframework.data.neo4j.core.schema.Relationship.Direction
import org.springframework.data.neo4j.core.schema.RelationshipProperties
import org.springframework.data.neo4j.core.schema.TargetNode

@Node("Amigo")
data class Amigo(
    @Id @GeneratedValue
    val id: String = "",
    @Relationship(type = "VIAJO_CON", direction = Direction.OUTGOING)
    var viajoCon: MutableList<Chofer> = mutableListOf(),
    @Property("nombre_y_apellido")
    val nombreYApellido: String
)

@Node("Chofer")
data class Chofer(
    @Id
    val id: String = ""
)

@RelationshipProperties
data class Viajes(
    @Id @GeneratedValue
    var id: String? = null,
    @TargetNode
    var chofer: Chofer? = null
)

fun Viajero.toAmigo() = Amigo(
    nombreYApellido = nombreYApellido()
)