package com.github.andreyzim.currencyapp.domain

interface CurrencyRepository {

    suspend fun fetchCurrency() : List<Currency>
}