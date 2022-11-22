package com.github.andreyzim.currencyapp.domain

interface HandleRequest {

    suspend fun handle(block : suspend () -> Unit) : CurrencyResult

    class Base(
        private val handleError: HandleError,
        private val repository: CurrencyRepository
    ) : HandleRequest {

        override suspend fun handle(block: suspend () -> Unit) = try {
            block.invoke()
            CurrencyResult.Success(repository.fetchCurrency())
        } catch (e: Exception) {
            CurrencyResult.Failure(handleError.handle(e))
        }
    }

}
