package io.github.ygreis.example.seeder

import io.github.ygreis.springdataseeder.AbstractDatabaseSeeder
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

@Component
class DatabaseSeeder(
    applicationContext: ApplicationContext,
) : AbstractDatabaseSeeder(applicationContext) {

    override fun run() {
        call<UserSeeder>()
        call<ProductSeeder>()
    }
}
