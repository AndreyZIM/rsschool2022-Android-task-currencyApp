package com.github.andreyzim.currencyapp.presentation

import com.github.andreyzim.currencyapp.domain.Currency
import com.github.andreyzim.currencyapp.domain.CurrencyResult

class CurrencyResultMapper(
    private val communications: CurrencyCommunications,
    private val mapper: Currency.Mapper<CurrencyUi>
): CurrencyResult.Mapper<Unit> {
    override fun map(list: List<Currency>, errorMessage: String) = communications.showState(
        if (errorMessage.isEmpty()) {
            if (list.isEmpty())
                communications.showList(list.map {it.map(mapper)})
            UiState.Success()
        } else
            UiState.Error(errorMessage)
    )
}