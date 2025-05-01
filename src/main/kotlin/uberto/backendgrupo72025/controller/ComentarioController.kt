package uberto.backendgrupo72025.controller

import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import uberto.backendgrupo72025.dto.CalificacionDTO
import uberto.backendgrupo72025.service.ComentarioService
import uberto.backendgrupo72025.service.UsuarioService
import uberto.backendgrupo72025.service.ViajeService

@RestController
//@CrossOrigin("*")
class ComentarioController(
    @Autowired val comentarioService: ComentarioService,
    @Autowired val viajeService: ViajeService,
    private val usuarioService: UsuarioService
) {

    @GetMapping("/comentarios")
    @Operation(summary = "Obtiene todos los comentarios")
    fun getAllComentarios() = comentarioService.getAll()

    @GetMapping("/comentariosParaConfirmar/{id}")
    @Operation(summary = "Obtiene todos los comentarios para confirmar")
    fun getAllComentariosParaConfirmar(
        @RequestHeader("Authorization") bearerToken: String,
        @PathVariable id: String
    ) = comentarioService.getComentariosConfirmar(bearerToken,id)

    @GetMapping("/comentario")
    @Operation(summary = "Devuelve los comentarios por usuario")
    fun getComentariosPorUsuario(
        @RequestHeader("Authorization") bearerToken: String
    ) = comentarioService.getComentarios(bearerToken)

    @DeleteMapping("eliminarComentario/{idComentario}")
    @Operation(summary = "Elimina un comentario realizado")
    fun eliminarComentario(
        @RequestHeader("Authorization") bearerToken: String,
        @PathVariable idComentario: String
    ) = usuarioService.eliminarComentario(bearerToken, idComentario)

    @PostMapping("/calificar")
    @Operation(summary = "Calificar un viaje realizado")
    fun calificarViaje(
        @RequestHeader("Authorization") bearerToken: String,
        @RequestBody calificacion: CalificacionDTO
    ) = usuarioService.calificarViaje(bearerToken, calificacion)
}