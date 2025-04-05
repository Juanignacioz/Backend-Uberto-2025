package uberto.backendgrupo72025.domain

import jakarta.persistence.*

@Entity
@DiscriminatorValue(value = "Standard")
class Simple(
    id: Long = 0,
    nombre: String = "",
    apellido: String = "",
    edad: Int = 0,
    username: String = "",
    contrasenia: String = "",
    telefono: Int = 0,
    esChofer: Boolean = true,
    foto: String = "",
    vehiculo: Vehiculo = Vehiculo(),
    precioBaseDelViaje: Double = 0.0
) : Conductor(id,nombre, apellido, edad, username, contrasenia, telefono, esChofer, foto, vehiculo, precioBaseDelViaje) {

    override fun calculoPlus(cantidadDePasajeros: Int, duracion: Int): Double = 1000.toDouble()
}

@Entity
@DiscriminatorValue(value = "Ejecutivo")
class Ejecutivo(
    id: Long = 0,
    nombre: String = "",
    apellido: String = "",
    edad: Int = 0,
    username: String = "",
    contrasenia: String = "",
    telefono: Int = 0,
    esChofer: Boolean = true,
    foto: String = "",
    vehiculo: Vehiculo = Vehiculo(),
    precioBaseDelViaje: Double = 0.0
) : Conductor(id,nombre, apellido, edad, username, contrasenia, telefono, esChofer, foto, vehiculo, precioBaseDelViaje) {

    @Transient
    override var mensaje: String = "No cumple con los requisitos de la antiguedad, ya que debe ser de menos de 10 años"

    override fun esValidaLaCondicion(vehiculo: Vehiculo) = vehiculo.antiguedad() <= 10

    override fun calculoPlus(cantidadDePasajeros: Int, duracion: Int): Double = costoPorCantidadDePasajeros(cantidadDePasajeros)

    private fun costoPorCantidadDePasajeros(cantidadDePasajeros: Int):Double = if (cantidadDePasajeros == 1) 2000.toDouble() else 1500.toDouble()
}

@Entity
@DiscriminatorValue(value = "Moto")
class Moto(
    id: Long = 0,
    nombre: String = "",
    apellido: String = "",
    edad: Int = 0,
    username: String = "",
    contrasenia: String = "",
    telefono: Int = 0,
    esChofer: Boolean = true,
    foto: String = "",
    vehiculo: Vehiculo = Vehiculo(),
    precioBaseDelViaje: Double = 0.0
) : Conductor(id,nombre, apellido, edad, username, contrasenia, telefono, esChofer, foto, vehiculo, precioBaseDelViaje) {

    override fun calculoPlus(cantidadDePasajeros: Int, duracion: Int): Double = costoPorDuracion(duracion)

    private fun costoPorDuracion(duracion: Int): Double = if (duracion <= 30) 500.toDouble() else 600.toDouble()
}