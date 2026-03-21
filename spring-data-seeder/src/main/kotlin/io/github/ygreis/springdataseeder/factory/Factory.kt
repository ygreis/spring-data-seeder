package io.github.ygreis.springdataseeder.factory

interface Factory<T> {
    fun definition(): T
}
