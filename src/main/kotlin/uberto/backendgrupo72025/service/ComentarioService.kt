package uberto.backendgrupo72025.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import uberto.backendgrupo72025.dto.CalificacionDTO
import uberto.backendgrupo72025.dto.ComentarioDTO
import uberto.backendgrupo72025.dto.toComentario
import uberto.backendgrupo72025.dto.toComentarioDTO
import uberto.backendgrupo72025.domain.*
import uberto.backendgrupo72025.repository.jpa.ComentarioRepository
import uberto.backendgrupo72025.repository.mongo.ConductorRepository
import uberto.backendgrupo72025.security.TokenUtils

@Service
class ComentarioService(
    val comentarioRepository: ComentarioRepository
) {
    @Autowired
    private lateinit var conductorRepository: ConductorRepository

    @Autowired
    lateinit var tokenUtils: TokenUtils

    fun getAll() = comentarioRepository.findAll()

    fun getComentarioById(idComentario: String?) = idComentario?.let { comentarioRepository.findById(it).orElseThrow{ NotFoundException("No se encontro el comentario con id $it") } }!!

    fun getComentarios(bearerToken : String): List<ComentarioDTO> {
        val (userID, esChofer) = tokenUtils.authenticate(bearerToken)
        return if (esChofer) {
            comentarioRepository.findByViajeConductorIdAndActive(userID).map { it.toComentarioDTO(it.viaje.viajero.nombreYApellido(), it.viaje.viajero.foto) }
        } else {
            comentarioRepository.findByViajeViajeroIdAndActive(userID).map {
                val conductor = conductorRepository.findById(it.viaje.conductorId!!).get()
                it.toComentarioDTO(conductor.nombreYApellido(), conductor.foto)
            }
        }
    }

    fun getComentariosConfirmar(bearerToken : String,id: String): List<ComentarioDTO> {
        val (userID, esChofer) = tokenUtils.authenticate(bearerToken)
        return comentarioRepository.findByViajeConductorIdAndActive(id).map { it.toComentarioDTO(it.viaje.viajero.nombreYApellido(), it.viaje.viajero.foto) }
    }

    fun calificar(calificacion: CalificacionDTO, viaje: Viaje, userID: String): Comentario {
        validarPuedeCalificar(userID, viaje)
        val comentario = calificacion.toComentario(viaje)
        comentarioRepository.save(comentario)
        return comentario
    }

    fun validarPuedeCalificar(idUsuario: String?, viaje: Viaje) {
        validarEsElViajero(idUsuario, viaje)
        validarNoCalificado(idUsuario, viaje)
    }

    fun validarEsElViajero(idUsuario: String?, viaje: Viaje) {
        if (idUsuario != viaje.viajero.id) throw BadRequestException("No se puede calificar un viaje en el que no participaste.")
    }

    fun validarNoCalificado(idUsuario: String?, viaje: Viaje) {
        if (viajeCalificado(viaje.id)) throw BadRequestException("No se puede calificar el mismo viaje más de una vez.")
    }

    fun viajeCalificado(idViaje : String) = comentarioRepository.existsByViajeIdAndActive(idViaje, true)

    fun eliminarComentario(userID: String, comentario: Comentario) {
        validarEliminarComentario(userID, comentario)
        comentario.active = false
        comentarioRepository.save(comentario)
    }

    fun validarEliminarComentario(idViajero: String?, comentario: Comentario) {
        if (idViajero != comentario.viaje.viajero.id) throw BadRequestException("No se puede eliminar un comentario realizado por otro usuario")
    }

    fun getCalificacionByConductor(conductorID: String?) = comentarioRepository.promedioEstrellasByConductor(conductorID)
}