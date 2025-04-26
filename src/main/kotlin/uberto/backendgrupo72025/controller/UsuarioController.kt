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

    @GetMapping("/perfil/{id}")
    @Operation(summary = "Devuelve los datos para el perfil")
    fun getUsuarioPerfil(
        @PathVariable id: String,
        @RequestParam esChofer: Boolean
    ) = userService.getUsuarioPerfil(id, esChofer)

    @PostMapping("/confirmar")
    @Operation(summary = "Contratar viaje")
    fun contratarViaje(@RequestBody viaje: ViajeDTO) = userService.contratarViaje(viaje)

    @PostMapping("/home/buscar")
    @Operation(summary = "Devuelve los choferes disponibles")
    fun getChoferesDisponiles(@RequestBody busquedaDTO: BusquedaDTO) = userService.getChoferesDisponibles(busquedaDTO)

    @DeleteMapping("/eliminarAmigo/{userId}/{friendId}")
    @Operation(summary = "Elimina a un amigo de la lista de amigos del viajero")
    fun eliminarAmigo(
        @PathVariable userId: String,
        @PathVariable friendId: String
    ) = userService.eliminarAmigo(userId, friendId)

    @PatchMapping("/actualizarUsuario/{id}")
    @Operation(summary = "Actualiza los datos del usuario")
    fun actualizarUsuario(@PathVariable id: String, @RequestBody usuarioDTO: UsuarioDTO) =
        userService.actualizarUsuario(id, usuarioDTO)

    @GetMapping("/buscarAmigos/{id}")
    @Operation(summary = "Busca los usuarios para agregar como amigos")
    fun buscarAmigos(
        @PathVariable id: String,
        @RequestParam query: String
    ) = userService.getViajerosParaAgregarAmigo(id, query)

    @PutMapping("/agregarAmigo/{userId}/{friendId}")
    @Operation(summary = "agrega a un amigo de la lista de amigos del viajero")
    fun agregarAmigo(
        @PathVariable userId: String,
        @PathVariable friendId: String
    ) = userService.agregarAmigo(userId, friendId)

    @PostMapping("/cargarSaldo/{id}")
    @Operation(summary = "Carga saldo a un usuario")
    fun cargarSaldo(
        @PathVariable id: String,
        @RequestParam esChofer: Boolean,
        @RequestParam monto: Double
    ) = userService.cargarSaldo(id, esChofer, monto)

    @PatchMapping("/actualizarImagen/{id}")
    @Operation(summary = "Actualiza los datos del usuario")
    fun actualizarImagen(@PathVariable id: String, @RequestParam esChofer: Boolean, @RequestParam imagen: String) =
        userService.actualizarImagen(id, imagen, esChofer)

}