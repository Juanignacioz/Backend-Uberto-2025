package uberto.backendgrupo72025.controller

import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import uberto.backendgrupo72025.dto.RegistroClicksDTO
import uberto.backendgrupo72025.service.ClickLogService

@RestController
class ClickLogsController(
    @Autowired val clickLogService: ClickLogService
) {

    @PostMapping("/registrar")
    @Operation(summary = "Registra un click en un conductor desde la busqueda")
    fun registrarClick(
        @RequestBody registroClicks: RegistroClicksDTO,
        @RequestHeader("Authorization") bearerToken: String
    ) = clickLogService.registrarClick(registroClicks, bearerToken)

    @GetMapping("/logs/chofer")
    fun obtenerClicksPorChofer(
        @RequestHeader("Authorization") bearerToken: String)
        = clickLogService.totalClicksDeUnConductor(bearerToken)


}

