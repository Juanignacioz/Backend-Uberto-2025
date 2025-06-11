package uberto.backendgrupo72025.config

import org.neo4j.cypherdsl.core.renderer.Dialect
import org.neo4j.driver.Driver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.neo4j.core.transaction.Neo4jTransactionManager
import org.neo4j.cypherdsl.core.renderer.Configuration as CypherConfiguration


@Configuration
class Neo4jConfig {

    @Bean
    fun cypherDslConfiguration(): CypherConfiguration =
        CypherConfiguration.newConfig().withDialect(Dialect.NEO4J_5).build()

    @Bean
    fun neo4jTransactionManager(driver: Driver): Neo4jTransactionManager {
        return Neo4jTransactionManager(driver)
    }
}
