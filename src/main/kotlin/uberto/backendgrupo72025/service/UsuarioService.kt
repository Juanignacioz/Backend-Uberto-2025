package uberto.backendgrupo72025.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional
import org.springframework.stereotype.Service
import uberto.backendgrupo72025.dto.*
import uberto.backendgrupo72025.domain.*
import uberto.backendgrupo72025.repository.jpa.*
import uberto.backendgrupo72025.repository.mongo.*
import uberto.backendgrupo72025.security.TokenUtils
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Service
class UsuarioService(
    val vehiculoService: VehiculoService,
    val viajeroRepository: ViajeroRepository,
    val conductorRepository: ConductorRepository,
    val viajeService: ViajeService,
    val comentarioService: ComentarioService,
    val ultimaBusquedaRepository: BusquedaRepository
) {
    @Autowired
    lateinit var tokenUtils: TokenUtils

    fun getViajeroById(id: String?) = id?.let {
        viajeroRepository.findById(it).orElseThrow { NotFoundException("El viajero con id $id no fue encontrado") }
    }!!

    fun getConductorById(id: String?) = id?.let {
        conductorRepository.findById(it).orElseThrow { NotFoundException("El conductor con id $id no fue encontrado") }
    }!!

    fun getUsuarioLogin(user: UsuarioLoginDTO): String? {
        var usuario: Usuario?
        usuario = viajeroRepository.findByUsernameAndContrasenia(user.usuario, user.contrasenia)
        if (usuario != null) {
            return tokenUtils.createToken(usuario.id, usuario.rol)
        }

        usuario = conductorRepository.findByUsernameAndContrasenia(user.usuario, user.contrasenia)
            ?: throw CredencialesInvalidasException()
        return tokenUtils.createToken(usuario.id, usuario.rol)
    }

    fun getUsuarioPerfil(bearerToken: String): PerfilDTO {
        val (userID, esChofer) = tokenUtils.decodificatorAuth(bearerToken)
        return if (esChofer) {
            getConductorById(userID).toPerfilDTO()
        } else {
            (viajeroRepository.findViajeroPerfilById(userID)).toPerfilDTO()
        }
    }

    @Transactional
    fun actualizarImagen(bearerToken: String, imagen: String): String {
        val (userID, esChofer) = tokenUtils.decodificatorAuth(bearerToken)
        lateinit var usuario: Usuario
        if (esChofer) {
            usuario = getConductorById(userID)
            usuario.foto = imagen
            conductorRepository.save(usuario)
        } else {
            usuario = getViajeroById(userID)
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
    fun actualizarUsuario(bearerToken: String, usuarioDTO: UsuarioDTO): PerfilDTO {
        val (userID, esChofer) = tokenUtils.decodificatorAuth(bearerToken)
        return if (esChofer) {
            actualizarChofer(userID, usuarioDTO.toPerfilChoferDTO())
        } else {
            actualizarViajero(userID, usuarioDTO.toPerfilViajeroDTO())
        }
    }

    fun validarSeRealizaronCambiosConductor(conductor: Conductor, choferDTO: PerfilChoferDTO) {
        if (!seRealizaronCambios(conductor, choferDTO, conductor.precioBaseDelViaje, choferDTO.precioBase) &&
            !vehiculoService.validarCambioVehiculo(conductor, choferDTO)
        )
            throw BadRequestException("No se realizaron cambios.")
    }

    @Transactional
    fun agregarAmigo(bearerToken: String, idAmigo: String?): AmigoDTO {
        val (userID, esChofer) = tokenUtils.decodificatorAuth(bearerToken)
        val viajero = getViajeroById(userID)
        val amigo = getViajeroById(idAmigo)
        viajero.agregarAmigo(amigo)
        viajeroRepository.save(viajero)
        return amigo.toAmigoDTO()
    }

    @Transactional
    fun eliminarAmigo(bearerToken: String, idAmigo: String?) {
        val (userID, esChofer) = tokenUtils.decodificatorAuth(bearerToken)
        val viajero = getViajeroById(userID)
        val amigo = getViajeroById(idAmigo)
        viajero.eliminarAmigo(amigo)
        viajeroRepository.save(viajero)
    }

    fun getViajerosParaAgregarAmigo(bearerToken: String, query: String): List<AmigoDTO> {
        val (userID, esChofer) = tokenUtils.decodificatorAuth(bearerToken)
        return viajeroRepository.buscarViajerosNoAmigos(userID, query).map { it.toAmigoDTO() }
    }


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
    fun cargarSaldo(bearerToken: String, monto: Double) {
        val (userID, esChofer) = tokenUtils.decodificatorAuth(bearerToken)
        val usuario = getViajeroById(userID)
        validarCargaDeSaldo(monto, esChofer)
        usuario.agregarSaldo(monto)
        viajeroRepository.save(usuario)
    }

    fun getChoferesDisponibles(bearerToken: String, busquedaDTO: BusquedaDTO): List<ConductorDTO> {
        val (userID, esChofer) = tokenUtils.decodificatorAuth(bearerToken)
        val nuevaFecha = LocalDateTime.parse(busquedaDTO.fecha, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
        val nuevaFechaFin = LocalDateTime.parse(busquedaDTO.fecha, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
            .plusMinutes(busquedaDTO.duracion.toLong())
        val conductoresDisponibles =
            conductorRepository.findConductoresDisponibles(nuevaFecha, nuevaFechaFin)
        val ultimaBusqueda = busquedaDTO.toUltimaBusqueda(userID, nuevaFecha)
        ultimaBusquedaRepository.save(ultimaBusqueda)
        return conductoresDisponibles.map {
            it.toConductorDTO(busquedaDTO.cantidadDePasajeros, busquedaDTO.duracion)
        }
    }

    fun conductorDisponible(idConductor: String?, fechaNueva: LocalDateTime, duracion: Int) =
        !viajeService.getViajesByUsuarioId(idConductor).any { it.seSolapan(fechaNueva, duracion) }

    @Transactional
    fun contratarViaje(viajeDTO: ViajeDTO, bearerToken: String) {
        val (userID, esChofer) = tokenUtils.decodificatorAuth(bearerToken)
        val viajero = getViajeroById(userID)
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
    fun calificarViaje(bearerToken: String, calificacion: CalificacionDTO) {
        val (userID, esChofer) = tokenUtils.decodificatorAuth(bearerToken)

        val viaje = viajeService.getViajeById(calificacion.idViaje)
        comentarioService.calificar(calificacion, viaje, userID)
        viaje.viajeComentado = true
        actualizarCalificacion(viaje.conductorId)
        viajeService.save(viaje)
    }

    fun validarUsuario(idUsuario: String) {
        if (viajeroRepository.existsById(idUsuario) || conductorRepository.existsById(idUsuario)) {
            return
        } else {
            throw CredencialesInvalidasException()
        }
    }

    @Transactional
    fun eliminarComentario(bearerToken: String, idComentario: String?) {
        val (userID, esChofer) = tokenUtils.decodificatorAuth(bearerToken)
        val comentario = comentarioService.getComentarioById(idComentario)
        comentarioService.eliminarComentario(userID, comentario)
        val viaje = comentario.viaje
        viaje.viajeComentado = false
        actualizarCalificacion(comentario.viaje.conductorId)
        viajeService.save(viaje)
    }

    private fun actualizarCalificacion(conductorId: String?) {
        val conductor = getConductorById(conductorId)
        conductor.calificacion = comentarioService.getCalificacionByConductor(conductor.id)
        conductorRepository.save(conductor)
    }

    fun getComentarios(bearerToken: String): List<ComentarioDTO> {
        val (userID, esChofer) = tokenUtils.decodificatorAuth(bearerToken)
        return if (esChofer) {
            comentarioService.getComentariosViajero(userID).map {
                it.toComentarioDTO(it.viaje.viajero.nombreYApellido(), it.viaje.viajero.foto)
            }
        } else {
            comentarioService.getComentariosConductor(userID).map {
                it.toComentarioDTO(it.viaje.nombreYApellidoConductor, it.viaje.fotoConductor)
            }
        }
    }


    fun getViajesRealizadosByUsuario(bearerToken: String): ViajesCompletadosDTO {

        val (userID, esChofer) = tokenUtils.decodificatorAuth(bearerToken)
        lateinit var viajesRealizadosDTO: List<ViajeDTO>
        var totalFacturado = 0.0

        if (esChofer) {
            viajesRealizadosDTO = viajeService.getViajesRealizadosByConductor(userID)
            totalFacturado = viajeService.getTotalFacturado(userID)
        } else {
            viajesRealizadosDTO = viajeService.getViajesRealizadosByViajero(userID).map {
                it.toViajeDTO(
                    it.nombreYApellidoConductor,
                    it.fotoConductor,
                    viajeService.viajeCalificable(it)
                )
            }
        }
        return ViajesCompletadosDTO(viajesRealizadosDTO, totalFacturado)
    }

    fun getViajesPendientesByUsuario(bearerToken: String): List<ViajeDTO> { //del viaje paso al user
        val (userID, esChofer) = tokenUtils.decodificatorAuth(bearerToken)

        return if (esChofer) {
            viajeService.getViajesPendientesByConductor(userID)
        } else {
            viajeService.getViajesPendientesByViajero(userID).map {
                it.toViajeDTO(
                    it.nombreYApellidoConductor,
                    it.fotoConductor,
                    viajeService.viajeCalificable(it)
                )
            }
        }
    }

    fun getUltimaBusquedaPorViajero(bearerToken: String): UltimaBusqueda? {
        val (userID, esChofer) = tokenUtils.decodificatorAuth(bearerToken)
        return ultimaBusquedaRepository.findByIdOrNull(userID) //uno solo y si no hay ninguno devuelve null
    }

}