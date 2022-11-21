package com.github.andreyzim.currencyapp.domain

data class Currency(
    private val date: String,
    private val rate: String,
    private val base: String,
) {
    interface Mapper<T> {
        fun map(date: String, rate: String, base: String): T
    }

    fun <T> map(mapper: Mapper<T>): T = mapper.map(date, rate, base)
}