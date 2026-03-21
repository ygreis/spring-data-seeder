package io.github.ygreis.example.seeder

import io.github.ygreis.example.repository.ProductRepository
import io.github.ygreis.example.repository.UserRepository
import io.github.ygreis.example.seeder.factory.ProductFactory
import io.github.ygreis.springdataseeder.Seeder
import org.springframework.stereotype.Component

@Component
class ProductSeeder(
    private val productFactory: ProductFactory,
    private val userRepository: UserRepository,
    private val productRepository: ProductRepository,
) : Seeder {

    override fun run() {
        val users = userRepository.findAll()

        users.forEach { user ->
            val products = productFactory.times(2).make { product ->
                product.copy(
                    userId = user.id,
                )
            }

            productRepository.saveAll(products)
        }
    }
}
