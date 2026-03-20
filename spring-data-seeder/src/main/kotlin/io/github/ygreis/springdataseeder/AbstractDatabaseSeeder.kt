package io.github.ygreis.springdataseeder

import org.springframework.context.ApplicationContext

abstract class AbstractDatabaseSeeder(
    private val applicationContext: ApplicationContext,
) : Seeder {

    protected fun call(seederClass: Class<out Seeder>) {
        val seeder = applicationContext.getBean(seederClass)
        seeder.run()
    }
}
