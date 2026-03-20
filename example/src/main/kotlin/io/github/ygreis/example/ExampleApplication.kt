package io.github.ygreis.example

import io.github.ygreis.example.seeder.DatabaseSeeder
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class ExampleApplication {

    @Bean
    fun seedRunner(databaseSeeder: DatabaseSeeder) = org.springframework.boot.CommandLineRunner {
        databaseSeeder.run()
    }
}

fun main(args: Array<String>) {
    runApplication<ExampleApplication>(*args)
}
