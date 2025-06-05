package uberto.backendgrupo72025.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories

@Configuration
@EnableJpaRepositories(basePackages = ["uberto.backendgrupo72025.repository.jpa"])
@EnableMongoRepositories(basePackages = ["uberto.backendgrupo72025.repository.mongo"])
class PersistenceConfig