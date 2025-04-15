package uberto.backendgrupo72025.service

import org.springframework.transaction.annotation.Transactional
import org.springframework.stereotype.Service
import uberto.backendgrupo72025.dto.*
import uberto.backendgrupo72025.domain.*
import uberto.backendgrupo72025.repository.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Service
class UsuarioService(
    val vehiculoService: VehiculoService,
    val viajeroRepository: ViajeroRepository,
    val conductorRepository: ConductorRepository,
    val viajeService: ViajeService,
    val comentarioService: ComentarioService
) {

    fun getViajeroById(id: String?) = id?.let { viajeroRepository.findById(it).orElseThrow { NotFoundException("El viajero con id $id no fue encontrado") } }!!

    fun getConductorById(id: String?) = id?.let { conductorRepository.findById(it).orElseThrow { NotFoundException("El conductor con id $id no fue encontrado") } }!!

    fun getUsuarioLogin(user: UsuarioLoginDTO): LoginDTO {
        var usuario: Usuario?
        usuario = viajeroRepository.findByUsernameAndContrasenia(user.usuario, user.contrasenia)
        if (usuario != null) { return usuario.toLoginDTO() }
        else {
            usuario = conductorRepository.findByUsernameAndContrasenia(user.usuario, user.contrasenia)
            return usuario?.toLoginDTO() ?: throw UnauthorizedException("Los datos ingresados son incorrectos")
        }
    }

    fun getUsuarioPerfil(id: String?, esChofer: Boolean): PerfilDTO {
        return if (esChofer) {
            getConductorById(id).toPerfilDTO()
        } else {
            (viajeroRepository.findViajeroPerfilById(id)).toPerfilDTO()
        }
    }

    @Transactional
    fun actualizarImagen(id: String?, imagen: String, esChofer: Boolean): String {
        lateinit var usuario: Usuario
        if (esChofer) {
            usuario = getConductorById(id)
            usuario.foto = imagen
            conductorRepository.save(usuario)
        } else {
            usuario = getViajeroById(id)
            usuario.foto = imagen
            viajeroRepository.save(usuario)
        }
        return usuario.foto
    }

    fun validarSeRealizaronCambios(usuario: Usuario, usuarioDTO: PerfilDTO, param1: Number, param2: Number) {
        if (!seRealizaronCambios(usuario, usuarioDTO, param1, param2)) {
            throw BadRequestException("No se realizó ningún cambio.")
        }
    }

    fun seRealizaronCambios(usuario: Usuario, usuarioDTO: PerfilDTO, param1: Number, param2: Number) =
        usuario.nombre != usuarioDTO.nombre || usuario.apellido != usuarioDTO.apellido || param1 != param2


    fun actualizarViajero(id: String?, viajeroDTO: PerfilViajeroDTO): PerfilViajeroDTO {
        val viajero = getViajeroById(id)
        validarSeRealizaronCambios(viajero, viajeroDTO, viajero.telefono, viajeroDTO.telefono)
        viajero.nombre = viajeroDTO.nombre
        viajero.apellido = viajeroDTO.apellido
        viajero.telefono = viajeroDTO.telefono
        viajeroRepository.save(viajero)
        return viajero.toPerfilDTO()
    }

    fun actualizarChofer(id: String?, choferDTO: PerfilChoferDTO): PerfilChoferDTO {
        val conductor = getConductorById(id)
        validarSeRealizaronCambiosConductor(conductor, choferDTO)
        val nuevoVehiculo = vehiculoService.actualizarVehiculo(conductor, choferDTO)
        conductor.nombre = choferDTO.nombre
        conductor.apellido = choferDTO.apellido
        conductor.precioBaseDelViaje = choferDTO.precioBase
        conductor.vehiculo.active = false
        conductor.vehiculo = nuevoVehiculo
        conductor.validar()
        conductorRepository.save(conductor)
        return conductor.toPerfilDTO()
    }

    @Transactional
    fun actualizarUsuario(id: String?, usuarioDTO: UsuarioDTO): PerfilDTO {
        return if (usuarioDTO.esChofer) {
            actualizarChofer(id, usuarioDTO.toPerfilChoferDTO())
        } else {
            actualizarViajero(id, usuarioDTO.toPerfilViajeroDTO())
        }
    }

    fun validarSeRealizaronCambiosConductor(conductor: Conductor, choferDTO: PerfilChoferDTO) {
        if (!seRealizaronCambios(conductor, choferDTO, conductor.precioBaseDelViaje, choferDTO.precioBase) &&
            !vehiculoService.validarCambioVehiculo(conductor, choferDTO)
        )
            throw BadRequestException("No se realizaron cambios.")
    }

    @Transactional
    fun agregarAmigo(idViajero: String?, idAmigo: String?): AmigoDTO {
        val viajero = getViajeroById(idViajero)
        val amigo = getViajeroById(idAmigo)
        viajero.agregarAmigo(amigo)
        viajeroRepository.save(viajero)
        return amigo.toAmigoDTO()
    }

    @Transactional
    fun eliminarAmigo(idViajero: String?, idAmigo: String?) {
        val viajero = getViajeroById(idViajero)
        val amigo = getViajeroById(idAmigo)
        viajero.eliminarAmigo(amigo)
        viajeroRepository.save(viajero)
    }

    fun getViajerosParaAgregarAmigo(id: String?, query: String) =
        viajeroRepository.buscarViajerosNoAmigos(id, query).map { it.toAmigoDTO() }


    fun validarSaldoPositivo(monto: Double) {
        if (monto <= 0 || monto != monto.toLong().toDouble()) {
            throw BadRequestException("El monto debe ser un número entero positivo.")
        }
    }

    fun validarEsChofer(esChofer: Boolean) {
        if (esChofer) {
            throw BadRequestException("Los choferes no pueden cargar saldo")
        }
    }

    fun validarCargaDeSaldo(monto: Double, esChofer: Boolean) {
        validarSaldoPositivo(monto)
        validarEsChofer(esChofer)
    }

    @Transactional
    fun cargarSaldo(id: String?, esChofer: Boolean, monto: Double) {
        val usuario = getViajeroById(id)
        validarCargaDeSaldo(monto, esChofer)
        usuario.agregarSaldo(monto)
        viajeroRepository.save(usuario)
    }

    fun getChoferesDisponibles(busquedaDTO: BusquedaDTO): List<ConductorDTO> {
        val nuevaFecha = LocalDateTime.parse(busquedaDTO.fecha, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
        val nuevaFechaFin = LocalDateTime.parse(busquedaDTO.fecha, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")).plusMinutes(busquedaDTO.duracion.toLong())
        return conductorRepository.findConductoresDisponibles(nuevaFecha, nuevaFechaFin).map {
            it.toConductorDTO(busquedaDTO.cantidadDePasajeros, busquedaDTO.duracion)
        }
    }

    fun conductorDisponible(idConductor: String?, fechaNueva: LocalDateTime, duracion: Int) =
        !viajeService.getViajesByUsuarioId(idConductor).any { it.seSolapan(fechaNueva, duracion) }

    @Transactional
    fun contratarViaje(viajeDTO: ViajeDTO) {
        val viajero = getViajeroById(viajeDTO.idViajero)
        val conductor = getConductorById(viajeDTO.idConductor)
        validarPuedeRealizarseViaje(viajero, conductor.id, viajeDTO)
        val viaje = viajeService.crearViaje(viajeDTO, viajero, conductor)
        viajero.contratarViaje(viaje)
        viajeroRepository.save(viajero)
    }

    fun validarPuedeRealizarseViaje(viajero: Viajero, idConductor: String?, viajeDTO: ViajeDTO) {
        conductorDisponible(
            idConductor,
            LocalDateTime.parse(viajeDTO.fechaInicio, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
            viajeDTO.duracion
        )
        viajero.validarSaldoSuficiente(viajeDTO.importe)
    }

    @Transactional
    fun calificarViaje(idUsuario: String?, calificacion: CalificacionDTO): ComentarioDTO {
        val viaje = viajeService.getViajeById(calificacion.idViaje)
        val comentario = comentarioService.calificar(calificacion, viaje, idUsuario)
        viaje.viajeComentado=true
        actualizarCalificacion(viaje.conductor)
        viajeService.save(viaje)
        return comentario.toComentarioDTO(viaje.conductor.nombreYApellido(), viaje.conductor.foto)
    }

    @Transactional
    fun eliminarComentario(idViajero: String?, idComentario: String?) {
        val comentario = comentarioService.getComentarioById(idComentario)
        comentarioService.eliminarComentario(idViajero, comentario)
        val viaje=comentario.viaje
        viaje.viajeComentado=false
        actualizarCalificacion(comentario.viaje.conductor)
        viajeService.save(viaje)
    }

    private fun actualizarCalificacion(conductor: Conductor) {
        conductor.calificacion = comentarioService.getCalificacionByConductor(conductor.id)
        conductorRepository.save(conductor)
    }
}