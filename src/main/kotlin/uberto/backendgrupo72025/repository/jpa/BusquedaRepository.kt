package uberto.backendgrupo72025.repository.jpa

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import uberto.backendgrupo72025.domain.Comentario
import uberto.backendgrupo72025.domain.UltimaBusqueda

@Repository
interface BusquedaRepository : CrudRepository<UltimaBusqueda, String?> {


}