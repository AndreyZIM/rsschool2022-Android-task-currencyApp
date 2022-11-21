package com.github.andreyzim.currencyapp.domain

sealed class CurrencyResult {

    interface Mapper<T> {
        fun map(list: List<Currency>, errorMessage: String) : T
    }

    abstract fun <T> map(mapper: Mapper<T>) : T

    data class Success(private val list: List<Currency> = emptyList()) : CurrencyResult() {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(list, "")
    }

    class Failure(private val message: String) : CurrencyResult() {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(emptyList() , message)
    }
}