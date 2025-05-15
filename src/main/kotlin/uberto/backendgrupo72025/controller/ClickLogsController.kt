package uberto.backendgrupo72025.controller

import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import uberto.backendgrupo72025.service.ClickLogService

@RestController
@RequestMapping("/logs")
class ClickLogsController(
    @Autowired val clickLogService: ClickLogService
) {

    @PostMapping("/registrar/{conductorNombre}")
    @Operation(summary = "Registra un click en un conductor desde la búsqueda")
    fun registrarClick(
        @PathVariable conductorNombre: String,
        @RequestHeader("Authorization") bearerToken: String
    ) = clickLogService.registrarClick(conductorNombre, bearerToken)

}