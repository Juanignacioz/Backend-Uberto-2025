package uberto.backendgrupo72025.controller

import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import uberto.backendgrupo72025.dto.*

import uberto.backendgrupo72025.service.UsuarioService


@RestController
class UsuarioController(@Autowired val userService: UsuarioService) {

    @PostMapping("/usuarioLogin")
    @Operation(summary = "Devuelve un usuario que coincida user y pass")
    fun postUsuarioLoggin(@RequestBody user: UsuarioLoginDTO) = userService.getUsuarioLogin(user)

    @GetMapping("/perfil")
    @Operation(summary = "Devuelve los datos para el perfil")
    fun getUsuarioPerfil(
        @RequestHeader("Authorization") bearerToken: String
    ) = userService.getUsuarioPerfil(bearerToken)

    @PostMapping("/confirmar")
    @Operation(summary = "Contratar viaje")
    fun contratarViaje(@RequestBody viaje: ViajeDTO) = userService.contratarViaje(viaje)

    @PostMapping("/home/buscar")
    @Operation(summary = "Devuelve los choferes disponibles")
    fun getChoferesDisponiles(@RequestBody busquedaDTO: BusquedaDTO) = userService.getChoferesDisponibles(busquedaDTO)

    @DeleteMapping("/eliminarAmigo/{friendId}")
    @Operation(summary = "Elimina a un amigo de la lista de amigos del viajero")
    fun eliminarAmigo(
        @RequestHeader("Authorization") bearerToken: String,
        @PathVariable friendId: String
    ) = userService.eliminarAmigo(bearerToken, friendId)

    @PatchMapping("/actualizarUsuario")
    @Operation(summary = "Actualiza los datos del usuario")
    fun actualizarUsuario(
        @RequestHeader("Authorization") bearerToken: String,
        @RequestBody usuarioDTO: UsuarioDTO) =
        userService.actualizarUsuario(bearerToken, usuarioDTO)

    @GetMapping("/buscarAmigos")
    @Operation(summary = "Busca los usuarios para agregar como amigos")
    fun buscarAmigos(
        @RequestHeader("Authorization") bearerToken: String,
        @RequestParam query: String
    ) = userService.getViajerosParaAgregarAmigo(bearerToken, query)

    @PutMapping("/agregarAmigo/{friendId}")
    @Operation(summary = "agrega a un amigo de la lista de amigos del viajero")
    fun agregarAmigo(
        @RequestHeader("Authorization") bearerToken: String,
        @PathVariable friendId: String
    ) = userService.agregarAmigo(bearerToken, friendId)

    @PostMapping("/cargarSaldo")
    @Operation(summary = "Carga saldo a un usuario")
    fun cargarSaldo(
        @RequestHeader("Authorization") bearerToken: String,
        @RequestParam monto: Double
    ) = userService.cargarSaldo(bearerToken, monto)

    @PatchMapping("/actualizarImagen")
    @Operation(summary = "Actualiza los datos del usuario")
    fun actualizarImagen(
        @RequestHeader("Authorization") bearerToken: String,
        @RequestParam imagen: String) =
        userService.actualizarImagen(bearerToken, imagen)

}