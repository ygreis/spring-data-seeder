package io.github.ygreis.springdataseeder.factory

abstract class AbstractFactory<T> : Factory<T> {

    fun make(): T {
        return definition()
    }

    fun make(transform: (T) -> T): T {
        return transform(definition())
    }

    fun times(total: Int): FactoryCollectionBuilder<T> {
        require(total >= 1) { "times(total) requires a value greater than or equal to 1." }
        return FactoryCollectionBuilder(this, total)
    }
}
