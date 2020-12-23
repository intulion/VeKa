package dev.akat.veka.mappers

interface Mapper<E, T> {

    fun map(from: E): T

    fun map(list: List<E>): List<T> = list.map { map(it) }
}
