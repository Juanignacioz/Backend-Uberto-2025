package uberto.backendgrupo72025.Domain

import uberto.backendgrupo72025.Repository.ItemRepo
import java.time.LocalDate

class Vehiculo(
//    var id: Long? = 0,
    var marca: String,
    var modelo : String,
    var dominio: String,
    var anio: Int,
    val tipoVehiculo : TipoVehiculo,
) : ItemRepo {
    override var id: Long = -1

    fun antiguedad(): Int = LocalDate.now().year - anio

    fun calculoPlusPorTipoVehiculo(cantidadDePasajeros: Int, duracion: Int): Double = tipoVehiculo.calculoPlus(cantidadDePasajeros, duracion)

    fun tipoVehiculo(): TipoVehiculo {
        return if (this.tipoVehiculo is Auto) {
            if(antiguedad()>10) Simple else Ejecutivo
        } else Moto
    }

    fun validar() {
        validarMarca()
        validarModelo()
        validarDominio()
        validarAnio()
        tipoVehiculo.validaLaCondicion(this)
    }

    fun esValidoMarca() = marca.isNotEmpty()
    fun validarMarca() {
        if (!esValidoMarca()) throw RuntimeException("La marca del vehículo esta vacia")
    }

    fun esValidoModelo() = modelo.isNotEmpty()
    fun validarModelo() {
        if (!esValidoModelo()) throw RuntimeException("El modelo del vehículo esta vacio")
    }

    fun esValidoDominio() = dominio.isNotEmpty()
    fun validarDominio() {
        if (!esValidoDominio()) throw RuntimeException("El dominio del vehículo esta vacia")
    }

    fun esValidoAnio() = anio >= 1867
    fun validarAnio() {
        if (!esValidoAnio()) throw RuntimeException("El año debe ser realista, no puede ser de un año anterior al de su invención")
    }
}

