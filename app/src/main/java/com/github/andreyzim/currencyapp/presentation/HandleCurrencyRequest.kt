package com.github.andreyzim.currencyapp.presentation

import com.github.andreyzim.currencyapp.domain.CurrencyResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

interface HandleCurrencyRequest {

    fun handle(
        coroutineScope: CoroutineScope,
        block: suspend () -> CurrencyResult
    )

    class Base(
        private val dispatchersList: DispatchersList,
        private val currencyCommunications: CurrencyCommunications,
        private val currencyResultMapper: CurrencyResult.Mapper<Unit>
    ) : HandleCurrencyRequest {

        override fun handle(coroutineScope: CoroutineScope, block: suspend () -> CurrencyResult) {
            currencyCommunications.showProgress(true)
            coroutineScope.launch(dispatchersList.io()) {
                val result = block.invoke()
                currencyCommunications.showProgress(false)
                result.map(currencyResultMapper)
            }
        }
    }
}