package com.github.andreyzim.currencyapp.domain

interface CurrencyInteractor {
    suspend fun fetchCurrency() : CurrencyResult

    class Base(
        private val repository: CurrencyRepository,
        private val handleRequest: HandleRequest
    ) : CurrencyInteractor {

        override suspend fun fetchCurrency(): CurrencyResult = handleRequest.handle {
            repository.fetchCurrency()
        }
    }
}