package uberto.backendgrupo72025.Controller

import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import uberto.backendgrupo72025.DTO.ViajeDTO
import uberto.backendgrupo72025.DTO.toViaje
import uberto.backendgrupo72025.Service.UsuarioService
import uberto.backendgrupo72025.Service.ViajeService


@RestController
@CrossOrigin("*")
class ViajeController(
    @Autowired val viajeService: ViajeService,
    @Autowired val usuarioService: UsuarioService
) {

    @PostMapping("/crearViaje")
    @Operation(summary = "crear viaje")
    fun crearViaje(@RequestBody viajeDTO: ViajeDTO) {
        //viajero.contratarViaje()
        viajeService.crearViaje(viajeDTO)
    }

    @GetMapping("/perfil/viajes")
    @Operation(summary = "crear viaje")
    fun buscarViaje() = viajeService.buscar()


    @GetMapping("/total-facturado/{idConductor}")
    @Operation(summary = "Obtener total facturado de todos los viajes finalizados de un conductor")
    fun getTotalFacturado(@PathVariable idConductor: Long): Double {
        return viajeService.getTotalFacturado(idConductor)
    }
}
