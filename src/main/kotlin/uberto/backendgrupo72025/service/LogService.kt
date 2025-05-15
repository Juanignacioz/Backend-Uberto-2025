package uberto.backendgrupo72025.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import uberto.backendgrupo72025.ClickLogs
import uberto.backendgrupo72025.repository.mongo.ClickLogRepository
import uberto.backendgrupo72025.security.TokenUtils
import java.time.LocalDateTime

@Service
class ClickLogService(
    val clickLogRepository: ClickLogRepository,
    val tokenUtils: TokenUtils
) {

    fun registrarClick(conductorNombre: String, bearerToken: String): ClickLogs {
        val (userId, _) = tokenUtils.decodificatorAuth(bearerToken)
        val log = ClickLogs(
            usuarioId = userId,
            conductorNombre = conductorNombre,
            fechaHora = LocalDateTime.now()
        )
        return clickLogRepository.save(log)
    }

}
