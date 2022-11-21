package com.github.andreyzim.currencyapp.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.github.andreyzim.currencyapp.domain.Currency

interface CurrencyCommunications : ObserveCurrency {

    fun showProgress(show: Boolean)

    fun showState(state: UiState)

    fun showList(list: List<CurrencyUi>)

    class Base(
        private val progress: ProgressCommunication,
        private val state: CurrencyStateCommunication,
        private val currencyList: CurrencyListCommunication
    ) : CurrencyCommunications {

        override fun showProgress(show: Boolean) = progress.map(show)

        override fun showState(state: UiState) = this.state.map(state)

        override fun showList(list: List<CurrencyUi>) = currencyList.map(list)

        override fun observeProgress(owner: LifecycleOwner, observer: Observer<Boolean>) =
            progress.observe(owner, observer)

        override fun observeState(owner: LifecycleOwner, observer: Observer<UiState>) =
            state.observe(owner, observer)

        override fun observeList(owner: LifecycleOwner, observer: Observer<List<CurrencyUi>>) =
            currencyList.observe(owner, observer)
    }

}

interface ObserveCurrency {

    fun observeProgress(owner: LifecycleOwner, observer: Observer<Boolean>)

    fun observeState(owner: LifecycleOwner, observer: Observer<UiState>)

    fun observeList(owner: LifecycleOwner, observer: Observer<List<CurrencyUi>>)
}

interface ProgressCommunication : Communication.Mutable<Boolean> {
    class Base : Communication.Post<Boolean>(), ProgressCommunication
}

interface CurrencyStateCommunication : Communication.Mutable<UiState> {
    class Base : Communication.Post<UiState>(), CurrencyStateCommunication
}

interface CurrencyListCommunication : Communication.Mutable<List<CurrencyUi>> {
    class Base : Communication.Post<List<CurrencyUi>>(), CurrencyListCommunication
}
