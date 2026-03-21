package io.github.ygreis.example.seeder.factory

import io.github.ygreis.example.entity.Product
import io.github.ygreis.springdataseeder.factory.AbstractFactory
import net.datafaker.Faker
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class ProductFactory : AbstractFactory<Product>() {

    private val faker = Faker()

    override fun definition(): Product {
        return Product(
            name = faker.commerce().productName(),
            price = faker.number().randomDouble(2, 1, 1000).toBigDecimal(),
        )
    }
}
