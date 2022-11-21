package com.github.andreyzim.currencyapp

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.github.andreyzim.currencyapp.presentation.CurrencyCommunications
import com.github.andreyzim.currencyapp.presentation.CurrencyUi
import com.github.andreyzim.currencyapp.presentation.UiState

abstract class BaseTest {

    protected class TestCurrencyCommunications : CurrencyCommunications {

        val progressCalledList = mutableListOf<Boolean>()
        val stateCalledList = mutableListOf<UiState>()
        var timesShowList = 0
        val currenciesList = mutableListOf<CurrencyUi>()

        override fun showProgress(show: Boolean) {
            progressCalledList.add(show)
        }

        override fun showState(state: UiState) {
            stateCalledList.add(state)
        }

        override fun showList(list: List<CurrencyUi>) {
            timesShowList++
            currenciesList.addAll(list)
        }

        override fun observeProgress(owner: LifecycleOwner, observer: Observer<Boolean>) = Unit
        override fun observeState(owner: LifecycleOwner, observer: Observer<UiState>) = Unit
        override fun observeList(owner: LifecycleOwner, observer: Observer<List<CurrencyUi>>) = Unit
    }

}