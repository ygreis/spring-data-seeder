package io.github.ygreis.example.repository

import io.github.ygreis.example.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>
