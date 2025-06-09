package uberto.backendgrupo72025.domain

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import jakarta.persistence.*
import org.springframework.data.annotation.Transient
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field


@Document(collection = "conductores")
//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include =
//    JsonTypeInfo.As.PROPERTY, property = "type")
//@JsonSubTypes(
//    JsonSubTypes.Type(value = Simple::class, name = "Standard"),
//    JsonSubTypes.Type(value = Ejecutivo::class, name = "Ejecutivo"),
//    JsonSubTypes.Type(value = Moto::class, name = "Moto")
//)
//@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "tipo_de_conductor", discriminatorType = DiscriminatorType.STRING)
abstract class Conductor(
    id: String? = null,
    nombre: String = "",
    apellido: String = "",
    edad: Int = 0,
    username: String = "",
    contrasenia: String = "",
    telefono: Int = 0,
    esChofer: Boolean = true,
    rol: ROLES = ROLES.CONDUCTOR,
    foto: String = "",
    var vehiculo: Vehiculo = Vehiculo(),
    var precioBaseDelViaje: Double = 0.0
) : Usuario(id,nombre, apellido, edad, username, contrasenia, telefono, esChofer, rol, foto) {

    var calificacion: Double = 0.0

    @Transient
    open var mensaje = ""

    override fun validacionesPorUsuario() {
        validarVehiculo()
        validarPrecioBaseDelViaje()
        validarCondicion(vehiculo)
    }

    private fun validarVehiculo() {
        vehiculo.validar()
    }

    private fun esValidoPrecioBaseDelViaje() = precioBaseDelViaje > 0
    private fun validarPrecioBaseDelViaje() {
        if (!esValidoPrecioBaseDelViaje()) throw BadRequestException("El precio base no puede ser menor o igual a 0")
    }

    fun importeViaje(cantidadDePasajeros: Int, duracion: Int) =
        precioBaseDelViaje + duracion * calculoPlusPorTipoVehiculo(cantidadDePasajeros, duracion)

    fun calculoPlusPorTipoVehiculo(cantidadDePasajeros: Int, duracion: Int): Double = calculoPlus(cantidadDePasajeros, duracion)

    abstract fun calculoPlus(cantidadDePasajeros: Int, duracion: Int): Double

    fun validarCondicion(vehiculo: Vehiculo) {
        if(!esValidaLaCondicion(vehiculo)) throw BadRequestException(mensaje)
    }

    open fun esValidaLaCondicion(vehiculo: Vehiculo): Boolean = true
}