package io.github.ygreis.springdataseeder.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.data.seeder")
data class SpringDataSeederProperties(
    var enabled: Boolean = true,
    var runOnStartup: Boolean = true,
)
