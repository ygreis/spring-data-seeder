package io.github.ygreis.springdataseeder

interface Seeder {
    val name: String
        get() = this::class.simpleName
            ?: throw IllegalStateException("Seeder must have a simple name")
    fun run()
}
