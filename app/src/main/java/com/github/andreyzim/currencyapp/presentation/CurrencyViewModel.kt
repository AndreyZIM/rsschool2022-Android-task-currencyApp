package com.github.andreyzim.currencyapp.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.andreyzim.currencyapp.domain.CurrencyInteractor

class CurrencyViewModel(
    private val handleCurrencyRequest: HandleCurrencyRequest,
    private val manageResources: ManageResources,
    private val communication: CurrencyCommunications,
    private val interactor: CurrencyInteractor
) : ViewModel(), ObserveCurrency, FetchCurrency {

    override fun observeProgress(owner: LifecycleOwner, observer: Observer<Boolean>) =
        communication.observeProgress(owner, observer)

    override fun observeState(owner: LifecycleOwner, observer: Observer<UiState>) =
        communication.observeState(owner, observer)

    override fun observeList(owner: LifecycleOwner, observer: Observer<List<CurrencyUi>>) =
        communication.observeList(owner, observer)

    override fun fetchCurrency() {
        handleCurrencyRequest.handle(viewModelScope) {
            interactor.fetchCurrency()
        }
    }
}

interface FetchCurrency {
    fun fetchCurrency()
}