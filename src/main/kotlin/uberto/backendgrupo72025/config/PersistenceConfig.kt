package uberto.backendgrupo72025.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories

@Configuration
@EnableJpaRepositories(basePackages = ["uberto.backendgrupo72025.repository.jpa"])
@EnableMongoRepositories(basePackages = ["uberto.backendgrupo72025.repository.mongo"])
@EnableRedisRepositories(basePackages = ["uberto.backendgrupo72025.repository.jpa"])
@EnableNeo4jRepositories(basePackages = ["uberto.backendgrupo72025.repository.neo4j"])
class PersistenceConfig