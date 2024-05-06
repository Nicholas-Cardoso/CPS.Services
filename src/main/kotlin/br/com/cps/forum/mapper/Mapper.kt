package br.com.cps.forum.mapper

interface Mapper<T, U> {
    fun map(t: T): U
}