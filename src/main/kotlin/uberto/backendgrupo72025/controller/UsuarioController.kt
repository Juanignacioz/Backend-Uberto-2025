package uberto.backendgrupo72025.controller

import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import uberto.backendgrupo72025.domain.UltimaBusqueda
import uberto.backendgrupo72025.dto.*
import uberto.backendgrupo72025.service.Neo4jService

import uberto.backendgrupo72025.service.UsuarioService


@RestController
class UsuarioController(
    @Autowired val usuarioService: UsuarioService,
    @Autowired val neo4jService: Neo4jService
) {

    @PostMapping("/usuarioLogin")
    @Operation(summary = "Devuelve un usuario que coincida user y pass")
    fun postUsuarioLoggin(
        @RequestBody user: UsuarioLoginDTO
    ) = usuarioService.getUsuarioLogin(user)

    @GetMapping("/perfil")
    @Operation(summary = "Devuelve los datos para el perfil")
    fun getUsuarioPerfil(
        @RequestHeader("Authorization") bearerToken: String
    ) = usuarioService.getUsuarioPerfil(bearerToken)

    @PostMapping("/confirmar")
    @Operation(summary = "Contratar viaje")
    fun contratarViaje(
        @RequestBody viaje: ViajeDTO,
        @RequestHeader("Authorization") bearerToken: String,
    ) = usuarioService.contratarViaje(viaje, bearerToken)

    @PostMapping("/home/buscar")
    @Operation(summary = "Devuelve los choferes disponibles")
    fun getChoferesDisponiles(
        @RequestBody busquedaDTO: BusquedaDTO,
        @RequestHeader("Authorization") bearerToken: String
    ) = usuarioService.getChoferesDisponibles(bearerToken, busquedaDTO)

    @DeleteMapping("/eliminarAmigo/{friendId}")
    @Operation(summary = "Elimina a un amigo de la lista de amigos del viajero")
    fun eliminarAmigo(
        @RequestHeader("Authorization") bearerToken: String,
        @PathVariable friendId: String
    ) = usuarioService.eliminarAmigo(bearerToken, friendId)

    @PatchMapping("/actualizarUsuario")
    @Operation(summary = "Actualiza los datos del usuario")
    fun actualizarUsuario(
        @RequestHeader("Authorization") bearerToken: String,
        @RequestBody usuarioDTO: UsuarioDTO
    ) = usuarioService.actualizarUsuario(bearerToken, usuarioDTO)

    @GetMapping("/buscarAmigos")
    @Operation(summary = "Busca los usuarios para agregar como amigos")
    fun buscarAmigos(
        @RequestHeader("Authorization") bearerToken: String,
        @RequestParam query: String
    ) = usuarioService.getViajerosParaAgregarAmigo(bearerToken, query)

    @PutMapping("/agregarAmigo/{friendId}")
    @Operation(summary = "agrega a un amigo de la lista de amigos del viajero")
    fun agregarAmigo(
        @RequestHeader("Authorization") bearerToken: String,
        @PathVariable friendId: String
    ) = neo4jService.agregarAmigo(bearerToken, friendId)

    @PostMapping("/cargarSaldo")
    @Operation(summary = "Carga saldo a un usuario")
    fun cargarSaldo(
        @RequestHeader("Authorization") bearerToken: String,
        @RequestParam monto: Double
    ) = usuarioService.cargarSaldo(bearerToken, monto)

    @PatchMapping("/actualizarImagen")
    @Operation(summary = "Actualiza los datos del usuario")
    fun actualizarImagen(
        @RequestHeader("Authorization") bearerToken: String,
        @RequestParam imagen: String
    ) = usuarioService.actualizarImagen(bearerToken, imagen)

    @GetMapping("/ultimaBusqueda")
    @Operation(summary = "Devuelve la ultima busqueda")
    fun getUltimaBusqueda(
        @RequestHeader("Authorization") bearerToken: String
    ): UltimaBusqueda? = usuarioService.getUltimaBusquedaPorViajero(bearerToken)

    @GetMapping("/amigos")
    @Operation(summary = "Devuelve la ultima busqueda")
    fun getAmigos(
        @RequestHeader("Authorization") bearerToken: String
    ): List<AmigoDTO> = neo4jService.getAmigos(bearerToken)

    @GetMapping("/sugerencias")
    @Operation(summary = "Trae las sugerencias de viajeros para agregar como amigos")
    fun getSugerenciasDeAmistad(
        @RequestHeader("Authorization") bearerToken: String
    ) = usuarioService.sugerenciasDeAmistad(bearerToken)

}