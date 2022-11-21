package com.github.andreyzim.currencyapp.domain

interface CurrencyInteractor {
    fun fetchCurrency() : CurrencyResult
}