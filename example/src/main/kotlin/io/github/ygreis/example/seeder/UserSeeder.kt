package io.github.ygreis.example.seeder

import io.github.ygreis.example.user.User
import io.github.ygreis.example.user.UserRepository
import io.github.ygreis.springdataseeder.Seeder
import org.springframework.stereotype.Component

@Component
class UserSeeder(
    private val userRepository: UserRepository,
) : Seeder {

    override fun run() {
        userRepository.saveAll(
            listOf(
                User(name = "Ada Lovelace", email = "ada@example.com"),
                User(name = "Grace Hopper", email = "grace@example.com"),
            )
        )
    }
}
