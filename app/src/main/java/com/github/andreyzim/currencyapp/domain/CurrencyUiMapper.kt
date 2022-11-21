package com.github.andreyzim.currencyapp.domain

import com.github.andreyzim.currencyapp.presentation.CurrencyUi

class CurrencyUiMapper: Currency.Mapper<CurrencyUi> {
    override fun map(date: String, rate: String, base: String): CurrencyUi = CurrencyUi(rate, base)
}