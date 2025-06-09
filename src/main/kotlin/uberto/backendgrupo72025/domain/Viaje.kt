package uberto.backendgrupo72025.domain

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import jakarta.validation.constraints.Min
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Entity
@Table(name = "viajes")
class Viaje(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: String = "",
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "viajero_id")
    val viajero: Viajero = Viajero(),
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conductor_id")
    val conductor: Conductor = Simple(),
    @Column(length = 50)
    val origen: String = "",
    @Column(length = 50)
    val destino: String = "",
    @Column(columnDefinition = "TIMESTAMP(0)")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val fechaInicio: LocalDateTime = LocalDateTime.now(),
    @Column(columnDefinition = "TIMESTAMP(0)")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val fechaFin: LocalDateTime = LocalDateTime.now(),
    @Column
    val cantidadDePasajeros: Int = 0,
    @Column
    val duracion: Int = 0,
    @Column
    var importe: Double = 0.0,
) {
    @Column (nullable = false)
    var viajeComentado : Boolean= false

    fun viajePendiente() = fechaFin.isAfter(LocalDateTime.now())

    fun viajeFinalizado()= !viajePendiente()

    fun seSolapan(fechaNueva: LocalDateTime, duracion: Int): Boolean {
        val nuevaFechaFin = fechaNueva.plusMinutes(duracion.toLong())
        return fechaNueva.isBefore(fechaFin) && nuevaFechaFin.isAfter(fechaInicio)
    }
}