package uberto.backendgrupo72025.domain

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import java.time.LocalDateTime

@RedisHash("ultimaBusqueda", timeToLive =180 )
data class UltimaBusqueda(
    @Id
    val viajeroId: String = "",
    val origen: String = "",
    val destino: String = "",
    val fecha: LocalDateTime = LocalDateTime.now(),
    val cantidadDePasajeros: Int = 0,
    val createdAt: LocalDateTime = LocalDateTime.now()
)