package uberto.backendgrupo72025.domain
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import java.time.LocalDateTime
import java.util.*

@RedisHash("ultimaBusqueda",timeToLive = 18000)
data class UltimaBusqueda(
    //@Id
    //@GeneratedValue(strategy = GenerationType.UUID)
    @Id
    val id: String = UUID.randomUUID().toString(),
    val viajeroId: String="",
    val origen: String="",
    val destino: String="",
    val fecha: LocalDateTime=LocalDateTime.now(),
    val cantidadDePasajeros: Int=0
)