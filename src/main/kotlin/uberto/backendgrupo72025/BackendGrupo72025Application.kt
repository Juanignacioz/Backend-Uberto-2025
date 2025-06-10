package uberto.backendgrupo72025

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootApplication
@EnableJpaRepositories(basePackages = ["uberto.backendgrupo72025.repository.jpa"])
@EnableMongoRepositories(basePackages = ["uberto.backendgrupo72025.repository.mongo"])
class BackendGrupo72025Application

fun main(args: Array<String>) {
	runApplication<BackendGrupo72025Application>(*args)
}
