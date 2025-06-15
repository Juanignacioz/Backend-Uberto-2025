package uberto.backendgrupo72025.domain.neo4j

import org.springframework.data.neo4j.core.schema.*
import uberto.backendgrupo72025.domain.Viajero
import java.time.LocalDateTime

@Node("Viajero")
class ViajeroNode(
    @Id
    @GeneratedValue
    val id: String = "",
//    @Relationship(type = "VIAJO_CON", direction = Relationship.Direction.OUTGOING)
//    var viajes: MutableList<ViajeRelation> = mutableListOf(),
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

//    fun agregarViaje(nuevoViaje: ViajeRelation) {
//        viajes.add(nuevoViaje)
//    }
}


@RelationshipProperties
data class ViajeRelation(
    @Id @GeneratedValue
    var id: String = "",
    @TargetNode
    var conductor: ConductorNode,
    val fechaDeFinalizacion: LocalDateTime
)