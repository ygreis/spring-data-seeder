package io.github.ygreis.example.seeder.factory

import io.github.ygreis.example.entity.User
import io.github.ygreis.springdataseeder.factory.AbstractFactory
import net.datafaker.Faker
import org.springframework.stereotype.Component
import java.util.concurrent.atomic.AtomicInteger

@Component
class UserFactory : AbstractFactory<User>() {

    private val faker = Faker()

    override fun definition(): User {
        return User(
            name = faker.name().fullName(),
            email = faker.internet().emailAddress(),
        )
    }
}
