package uberto.backendgrupo72025.domain.neo4j

import org.springframework.data.neo4j.core.schema.*
import uberto.backendgrupo72025.domain.Viajero

@Node("Viajero")
class ViajeroNode(
    @Id
    @GeneratedValue
    val id: String = "",
    @Relationship(type = "VIAJO_CON", direction = Relationship.Direction.OUTGOING)
    var viajes: MutableList<ViajeRelation> = mutableListOf(),
    @Relationship(type = "AMISTAD", direction = Relationship.Direction.OUTGOING)
    var amigos: MutableList<ViajeroNode> = mutableListOf(),
    @Property("nombre_y_apellido")
    val nombreYApellido: String = "",
    val foto: String = "",
    val username: String = "",
    val viajeroId: String? = "",
) {
    constructor(viajero: Viajero) : this(
        viajeroId = viajero.id,
        nombreYApellido = viajero.nombreYApellido(),
        foto = viajero.foto,
        username = viajero.username
    )

    fun agregarViaje(nuevoViaje: ViajeRelation) {
        viajes.add(nuevoViaje)
    }

    fun agregarAmigo(amigo: ViajeroNode) {
        amigos.add(amigo)
    }
}


