package uberto.backendgrupo72025.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import jakarta.validation.constraints.Min

@Entity
@Table(name = "viajeros")
class Viajero(
    id: String? = null,
    nombre: String="",
    apellido: String="",
    edad: Int=0,
    username: String="",
    contrasenia: String="",
    telefono: Int=0,
    esChofer: Boolean=false,
    rol : ROLES = ROLES.VIAJERO,
    foto : String="",
    @Column
    var saldo: Double = 0.0,
    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinTable(
        name = "amistades",
        joinColumns = [JoinColumn(name = "viajero_id")],
        inverseJoinColumns = [JoinColumn(name = "amigo_id")]
    )
    val amigos: MutableList<Viajero> = mutableListOf()
) : Usuario(id,nombre, apellido, edad, username, contrasenia, telefono, esChofer,rol,foto) {


    override fun validacionesPorUsuario() {
        validarSaldo()
    }

    fun agregarSaldo(saldoAAgregar: Double) {
        saldo += saldoAAgregar
    }

    private fun esSaldoValido() = saldo >= 0.0
    private fun validarSaldo() {
        if (!esSaldoValido()) throw RuntimeException("El saldo no puede ser menor a 0")
    }

    fun agregarAmigo(viajero: Viajero) {
        validarAmigoExistente(viajero)

        amigos.add(viajero)
    }

    fun eliminarAmigo(viajero: Viajero) {
        validarAmigoNoExistente(viajero)
        amigos.remove(viajero)
    }

    fun validarAmigoExistente(viajero: Viajero) {
        if (esAmigo(viajero)) throw BadRequestException("Ya es amigo")
    }

    fun validarAmigoNoExistente(viajero: Viajero) {
        if (!esAmigo(viajero)) throw BadRequestException("Amigo inexistente")
    }

    fun esAmigo(viajero: Viajero) = amigos.contains(viajero)

    fun validarSaldoSuficiente(costoDelViaje: Double) {
        if (!saldoSuficiente(costoDelViaje)) throw BadRequestException("Saldo insuficiente.")

    }
    fun saldoSuficiente(costoDelViaje: Double) = saldo >= costoDelViaje

    fun contratarViaje(viaje: Viaje) {
        descontarSaldo(viaje.importe)
    }

    fun descontarSaldo(costoDelViaje: Double) { saldo -= costoDelViaje }
}

