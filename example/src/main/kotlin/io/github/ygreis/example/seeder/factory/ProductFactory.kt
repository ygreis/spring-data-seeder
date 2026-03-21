package io.github.ygreis.example.seeder.factory

import io.github.ygreis.example.entity.Product
import io.github.ygreis.springdataseeder.factory.AbstractFactory
import net.datafaker.Faker
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.util.concurrent.atomic.AtomicInteger

@Component
class ProductFactory : AbstractFactory<Product>() {

    private val counter = AtomicInteger(1)
    private val faker = Faker()

    override fun definition(): Product {
        val value = counter.getAndIncrement()
        return Product(
            name = "Product $value",
            price = faker.number().randomDouble(2, 10, 100).toBigDecimal(),
        )
    }
}
