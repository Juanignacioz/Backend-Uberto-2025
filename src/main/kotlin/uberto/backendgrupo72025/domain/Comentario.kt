package uberto.backendgrupo72025.domain

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import java.time.LocalDate

@Entity
@Table(name = "comentarios")
data class Comentario(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: String = "",
    @ManyToOne(fetch = FetchType.LAZY)
    val viaje: Viaje = Viaje(),
    @Column
    var estrellas: Int = 0,
    @Column
    var mensaje: String = "",
    @Column
    var fecha : LocalDate = LocalDate.now(),
    @Column(nullable = false)
    var active: Boolean = true
)