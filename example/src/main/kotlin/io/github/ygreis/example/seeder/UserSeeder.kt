package io.github.ygreis.example.seeder

import io.github.ygreis.example.seeder.factory.UserFactory
import io.github.ygreis.example.repository.UserRepository
import io.github.ygreis.springdataseeder.Seeder
import org.springframework.stereotype.Component

@Component
class UserSeeder(
    private val userFactory: UserFactory,
    private val userRepository: UserRepository,
) : Seeder {

    override fun run() {
        val users = userFactory.times(10).make()
        userRepository.saveAll(users)
    }
}
