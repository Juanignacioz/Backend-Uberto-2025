package perritoteam.nochesmagicas.config

import org.neo4j.cypherdsl.core.renderer.Dialect
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.neo4j.cypherdsl.core.renderer.Configuration as CypherConfiguration

@Configuration
class PersistenceConfig {

    @Bean
    fun cypherDslConfiguration(): CypherConfiguration =
        CypherConfiguration.newConfig().withDialect(Dialect.NEO4J_5).build()

}
