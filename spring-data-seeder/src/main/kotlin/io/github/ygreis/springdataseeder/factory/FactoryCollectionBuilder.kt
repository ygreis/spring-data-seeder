package io.github.ygreis.springdataseeder.factory

class FactoryCollectionBuilder<T>(
    private val factory: Factory<T>,
    private val total: Int,
) {

    fun make(): List<T> {
        return List(total) { factory.definition() }
    }

    fun make(transform: (T) -> T): List<T> {
        return List(total) { transform(factory.definition()) }
    }
}
