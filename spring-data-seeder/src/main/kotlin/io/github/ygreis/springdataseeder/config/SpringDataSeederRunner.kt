package io.github.ygreis.springdataseeder.config

import io.github.ygreis.springdataseeder.AbstractDatabaseSeeder
import org.springframework.boot.CommandLineRunner

class SpringDataSeederRunner(
    private val databaseSeeder: AbstractDatabaseSeeder,
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        databaseSeeder.run()
    }
}
