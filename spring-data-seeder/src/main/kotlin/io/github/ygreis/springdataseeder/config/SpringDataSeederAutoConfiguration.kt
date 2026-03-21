package io.github.ygreis.springdataseeder.config

import io.github.ygreis.springdataseeder.AbstractDatabaseSeeder
import io.github.ygreis.springdataseeder.Seeder
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean

@AutoConfiguration
@EnableConfigurationProperties(SpringDataSeederProperties::class)
class SpringDataSeederAutoConfiguration {

    @Bean
    fun springDataSeederRunner(
        properties: SpringDataSeederProperties,
        seeders: List<Seeder>,
        aggregateSeeders: List<AbstractDatabaseSeeder>,
    ): SpringDataSeederRunner {
        return SpringDataSeederRunner(
            properties = properties,
            seeders = seeders,
            aggregateSeeders = aggregateSeeders,
        )
    }
}
