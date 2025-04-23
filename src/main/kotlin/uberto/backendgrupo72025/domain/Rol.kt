package uberto.backendgrupo72025.domain

import jakarta.persistence.*



//@Entity
//@Table(name = "roles")
//class Rol(
//) {
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    var id: Long? = null
//    var name: String = ""
//}

enum class ROLES(val roleName: String) {
    CONDUCTOR("CONDUCTOR"), VIAJERO("VIAJERO")
}