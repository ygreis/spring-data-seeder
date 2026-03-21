package io.github.ygreis.springdataseeder.config

import io.github.ygreis.springdataseeder.AbstractDatabaseSeeder
import io.github.ygreis.springdataseeder.Seeder
import org.apache.commons.logging.LogFactory
import org.springframework.boot.CommandLineRunner

class SpringDataSeederRunner(
    private val properties: SpringDataSeederProperties,
    private val seeders: List<Seeder>,
    private val aggregateSeeders: List<AbstractDatabaseSeeder>,
) : CommandLineRunner {

    private val logger = LogFactory.getLog(javaClass)

    override fun run(vararg args: String?) {
        if (!properties.enabled) {
            return
        }

        val target = properties.target?.trim()?.takeIf { it.isNotEmpty() }
        val seeder = if (target == null) {
            resolveAggregateSeeder()
        } else {
            resolveTargetSeeder(target)
        }

        logger.info("Executing seeder: ${seeder.name}")
        seeder.run()
    }

    private fun resolveTargetSeeder(target: String): Seeder {
        val matchingSeeders = seeders.filter { it.name.equals(target, ignoreCase = true) }

        return when {
            matchingSeeders.isEmpty() -> {
                throw IllegalStateException(
                    "Seeder '$target' was not found. Available seeders: ${availableSeederNames()}."
                )
            }

            matchingSeeders.size > 1 -> {
                val matches = matchingSeeders.joinToString(", ") { it.javaClass.name }
                throw IllegalStateException(
                    "Multiple seeders matched '$target': $matches."
                )
            }

            else -> matchingSeeders.single()
        }
    }

    private fun resolveAggregateSeeder(): AbstractDatabaseSeeder {
        return when {
            aggregateSeeders.isEmpty() -> {
                throw IllegalStateException(
                    "No aggregate seeder found. Define exactly one bean extending AbstractDatabaseSeeder."
                )
            }

            aggregateSeeders.size > 1 -> {
                val matches = aggregateSeeders.joinToString(", ") { it.javaClass.name }
                throw IllegalStateException(
                    "Multiple aggregate seeders found: $matches. Define exactly one bean extending AbstractDatabaseSeeder."
                )
            }

            else -> aggregateSeeders.single()
        }
    }

    private fun availableSeederNames(): String {
        if (seeders.isEmpty()) {
            return "none"
        }

        return seeders
            .map { it.name }
            .sorted()
            .joinToString(", ")
    }
}
