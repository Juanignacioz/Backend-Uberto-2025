package uberto.backendgrupo72025.domain



import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import org.springframework.data.annotation.Id


@Document("viaje_resumido")
data class DataViaje(
    //@Id
//    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String? = "",
    val conductorId: String? = "",
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val fechaInicio: LocalDateTime = LocalDateTime.now(),
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val fechaFin: LocalDateTime = LocalDateTime.now()
)