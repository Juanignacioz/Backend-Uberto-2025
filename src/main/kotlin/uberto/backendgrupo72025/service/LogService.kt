package uberto.backendgrupo72025.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import uberto.backendgrupo72025.ClickLogs
import uberto.backendgrupo72025.dto.RegistroClicksDTO
import uberto.backendgrupo72025.repository.mongo.ClickLogRepository
import uberto.backendgrupo72025.security.TokenUtils
import java.time.LocalDateTime

@Service
class ClickLogService(
    val clickLogRepository: ClickLogRepository,
    val tokenUtils: TokenUtils
) {

    fun registrarClick(registroClicks: RegistroClicksDTO, bearerToken: String): ClickLogs {
        val (userId, esChofer) = tokenUtils.decodificatorAuth(bearerToken)
        val log = ClickLogs(
            usuarioId = userId,
            conductorNombre = registroClicks.conductorNombre,
            conductorId = registroClicks.conductorId,

        )
        return clickLogRepository.save(log)
    }

    fun totalClicksDeUnConductor(bearerToken: String): Int{
        val (userId, esChofer) = tokenUtils.decodificatorAuth(bearerToken)
        return clickLogRepository.countByConductorId(userId)
    }

}
