package uberto.backendgrupo72025.repository.jpa

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import uberto.backendgrupo72025.domain.Comentario
import uberto.backendgrupo72025.domain.UltimaBusqueda

@Repository
interface BusquedaRepository : CrudRepository<UltimaBusqueda, String?> {


    fun findTopByViajeroIdOrderByFechaDesc(viajeroId: String): UltimaBusqueda? //lo ordena desenciende y encuentra el ultimo

    fun findByFecha_Max(viajeroId: String): UltimaBusqueda?

    fun findTopByViajeroIdOrderByttlDesc(viajeroId: String): UltimaBusqueda? //lo ordena desenciende y encuentra el ultimo

}