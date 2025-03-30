package uberto.backendgrupo72025

import uberto.backendgrupo72025.domain.*
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import uberto.backendgrupo72025.repository.*
import java.time.LocalDateTime
import java.time.LocalDate

@Component
class UbertoBootstrap(
    val viajeroRepository: ViajeroRepository,
    val conductorRepository: ConductorRepository,
    val vehiculoRepository: VehiculoRepository,
    val viajeRepository: ViajeRepository,
    val comentarioRepository: ComentarioRepository,
    val usuarioRepository: UsuarioRepository,

    ) : InitializingBean {

    override fun afterPropertiesSet() {
        crearUsuarios()
        crearVehiculos()
        crearChoferes()
        crearViajes()
        crearComentarios()
    }

    // VIAJEROS
    val viajero1 = Viajero(
        nombre = "Juan",
        apellido = "Pérez",
        edad = 28,
        username = "juanp",
        contrasenia = "pass123",
        telefono = 123456789,
        esChofer = false,
        foto = "",
        saldo = 1500.50,
        amigos = mutableListOf()
    )
    val viajero2 = Viajero(
        nombre = "María",
        apellido = "González",
        edad = 34,
        username = "mariag",
        contrasenia = "secure456",
        telefono = 987654321,
        esChofer = false,
        foto = "",
        saldo = 2300.75,
        amigos = mutableListOf()
    )
    val viajero3 = Viajero(
        nombre = "Carlos",
        apellido = "López",
        edad = 23,
        username = "carlosl",
        contrasenia = "mypwd789",
        telefono = 456789123,
        esChofer = false,
        foto = "",
        saldo = 800.25,
        amigos = mutableListOf()
    )
    val viajero4 = Viajero(
        nombre = "Ana",
        apellido = "Martínez",
        edad = 41,
        username = "anam",
        contrasenia = "password1",
        telefono = 321654987,
        esChofer = false,
        foto = "",
        saldo = 3500.00,
        amigos = mutableListOf()
    )

    fun crearUsuarios() {
        listOf(viajero1, viajero2, viajero3, viajero4).forEach { usuarioRepository.save(it) }
    }

    // VEHICULOS
    final val vehiculoSimple = Vehiculo(marca = "Toyota", modelo = "Corolla", dominio = "ABC123", anio = 2018)
    final val vehiculoEjecutivo = Vehiculo(marca = "Ford", modelo = "Focus", dominio = "DEF456", anio = 2020)
    final val vehiculoMoto = Vehiculo(marca = "Yamaha", modelo = "FZ25", dominio = "MNO345", anio = 2022)

    fun crearVehiculos() {
        listOf(vehiculoSimple, vehiculoEjecutivo, vehiculoMoto).forEach { vehiculoRepository.save(it) }
    }

    // CONDUCTORES
    val conductor1 = Simple(
        nombre = "Luis",
        apellido = "Fernández",
        edad = 35,
        username = "luisf",
        contrasenia = "pass1234",
        telefono = 111222333,
        esChofer = true,
        foto = "",
        vehiculo = vehiculoSimple,
        precioBaseDelViaje = 400.0
    )
    val conductor2 = Ejecutivo(
        nombre = "Elena",
        apellido = "Ramírez",
        edad = 29,
        username = "elenaR",
        contrasenia = "secure789",
        telefono = 444555666,
        esChofer = true,
        foto = "",
        vehiculo = vehiculoEjecutivo,
        precioBaseDelViaje = 600.0
    )
    val conductor3 = Moto(
        nombre = "Pedro",
        apellido = "Sánchez",
        edad = 40,
        username = "pedros",
        contrasenia = "mypass567",
        telefono = 777888999,
        esChofer = true,
        foto = "",
        vehiculo = vehiculoMoto,
        precioBaseDelViaje = 300.0
    )

    fun crearChoferes() {
        listOf(conductor1, conductor2, conductor3).forEach { usuarioRepository.save(it) }
    }

    // VIAJES
    fun crearViajes() {
        val viajes = mutableListOf<Viaje>()
        val conductores = listOf(conductor1, conductor2, conductor3)
        val viajeros = listOf(viajero1, viajero2, viajero3, viajero4)

        for (conductor in conductores) {
            repeat(8) {
                val viajero = viajeros.random()
                val fechaInicio = if (it % 2 == 0) LocalDateTime.now().minusDays(it.toLong()) else LocalDateTime.now()
                    .plusDays(it.toLong())
                val duracion = (5..20).random()

                viajes.add(
                    Viaje(
                        viajero = viajero,
                        conductor = conductor,
                        origen = "Ciudad ${it + 1}",
                        destino = "Destino ${it + 1}",
                        fechaInicio = fechaInicio,
                        fechaFin = fechaInicio.plusMinutes(duracion.toLong()),
                        cantidadDePasajeros = (1..3).random(),
                        duracion = duracion,
                        importe = conductor.importeViaje((1..3).random(), (5..20).random())
                    )
                )
            }
        }
        viajes.forEach { viajeRepository.save(it) }
    }

    // COMENTARIOS
    fun crearComentarios() {
        val viajesRealizados = viajeRepository.findAll().filter { it.fechaInicio.isBefore(LocalDateTime.now()) }
        val comentarios = mutableListOf<Comentario>()

        viajesRealizados.take(5).forEach {
            comentarios.add(
                Comentario(
                    viaje = it,
                    estrellas = (3..5).random(),
                    mensaje = "Comentario sobre el viaje de ${it.viajero.nombre} con ${it.conductor.nombre}.",
                    fecha = LocalDate.now()
                )
            )
        }
        comentarios.forEach { comentarioRepository.save(it) }
    }
}