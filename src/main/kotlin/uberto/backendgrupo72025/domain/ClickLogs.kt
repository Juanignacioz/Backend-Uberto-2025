package uberto.backendgrupo72025
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "click_logs")
data class ClickLogs(
    @Id
    val id: String? = null,
    val conductorNombre: String,
    val conductorId: String? = null,
    val usuarioId: String,
    val fechaHora: LocalDateTime = LocalDateTime.now()
)
