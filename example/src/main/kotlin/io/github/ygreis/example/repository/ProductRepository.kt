package io.github.ygreis.example.repository

import io.github.ygreis.example.entity.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long>
