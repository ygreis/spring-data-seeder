package io.github.ygreis.springdataseeder.config

import io.github.ygreis.springdataseeder.AbstractDatabaseSeeder
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean

@AutoConfiguration
@EnableConfigurationProperties(SpringDataSeederProperties::class)
@ConditionalOnProperty(
    prefix = "spring.data.seeder",
    name = ["enabled"],
    havingValue = "true",
    matchIfMissing = true,
)
class SpringDataSeederAutoConfiguration {

    @Bean
    @ConditionalOnBean(AbstractDatabaseSeeder::class)
    @ConditionalOnProperty(
        prefix = "spring.data.seeder",
        name = ["run-on-startup"],
        havingValue = "true",
        matchIfMissing = true,
    )
    fun springDataSeederRunner(databaseSeeder: AbstractDatabaseSeeder): SpringDataSeederRunner {
        return SpringDataSeederRunner(databaseSeeder)
    }
}
