package uberto.backendgrupo72025.domain
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import java.time.LocalDateTime
import java.util.*

@RedisHash("ultimaBusqueda")
data class UltimaBusqueda(
    @Id
    val id: String = UUID.randomUUID().toString(),
    @TimeToLive
    val ttl: Long = 18000,
    val viajeroId: String="",
    val origen: String="",
    val destino: String="",
    val fecha: LocalDateTime=LocalDateTime.now(),
    val cantidadDePasajeros: Int=0
)