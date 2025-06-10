package uberto.backendgrupo72025.config

import jakarta.persistence.EntityManagerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.TransactionManagementConfigurer
import uberto.backendgrupo72025.repository.neo4j.ViajeroNodeRepository

@Configuration
@EnableJpaRepositories(basePackageClasses = [ViajeroNodeRepository::class])
class TransactionManagerConfig(
    private val entityManagerFactory: EntityManagerFactory
) : TransactionManagementConfigurer {

    @Bean
    fun transactionManager(): PlatformTransactionManager {
        return JpaTransactionManager(entityManagerFactory)
    }

    override fun annotationDrivenTransactionManager(): PlatformTransactionManager {
        return transactionManager()
    }
}