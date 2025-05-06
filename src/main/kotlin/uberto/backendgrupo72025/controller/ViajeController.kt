package uberto.backendgrupo72025.controller

import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import uberto.backendgrupo72025.dto.*
import uberto.backendgrupo72025.service.ViajeService


@RestController
//@CrossOrigin("*")
class ViajeController(
    @Autowired val viajeService: ViajeService
) {

    @GetMapping("/perfil/viajes")
    @Operation(summary = "crear viaje")
    fun getAllViajes() = viajeService.getAllViajes()

    @PostMapping("/filtrar")
    @Operation(summary = "Devuelve los viajes pendientes filtrados para el home chofer")
    fun getViajesFiltrados(
        @RequestHeader("Authorization") bearerToken: String,
        @RequestBody filtroDTO: FiltroDTO
    ) = viajeService.getViajesConductorFiltrados(bearerToken,filtroDTO)

    @GetMapping("/viajesRealizados")
    @Operation(summary = "Devuelve los viajes realizados")
    fun getViajesRealizadosPorUsuario(
        @RequestHeader("Authorization") bearerToken: String
    ) = viajeService.getViajesRealizadosByUsuario(bearerToken)

    @GetMapping("/viajesPendientes")
    @Operation(summary = "Devuelve los viajes Pendientes")
    fun getViajesPendientesPorUsuario(
        @RequestHeader("Authorization") bearerToken: String
    ) = viajeService.getViajesPendientesByUsuario(bearerToken)


}