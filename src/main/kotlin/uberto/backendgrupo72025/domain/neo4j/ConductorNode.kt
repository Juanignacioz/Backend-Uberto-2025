package uberto.backendgrupo72025.domain.neo4j

import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Property
import uberto.backendgrupo72025.domain.Conductor

@Node("Chofer")
data class ConductorNode(
    @Id
    @GeneratedValue
    val id: String? = "",
    @Property("nombre_y_apellido")
    val nombreYApellido: String = "",
    val conductorId: String? = "",
) {
    constructor(conductor: Conductor) : this(
        conductorId = conductor.id,
        nombreYApellido = conductor.nombreYApellido(),
    )
}