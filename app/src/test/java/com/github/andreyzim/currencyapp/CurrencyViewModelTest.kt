package com.github.andreyzim.currencyapp

import org.junit.Assert.assertEquals
import org.junit.Test

class CurrencyViewModelTest {

    @Test
    fun `test init and re-init`() {
        val communications = TestCurrencyCommunications()
        val interactor = TestCurrencyInteractor()

        val viewModel = CurrencyViewModel(communications, interactor)
        interactor.changeExpectedResult(CurrencyResult.Success())

        viewModel.init(isFirstRun = true)

        assertEquals(true, communications.progressCalledList[0])
        assertEquals(2, communications.progressCalledList.size)
        assertEquals(false, communications.progressCalledList[1])

        assertEquals(1, communications.stateCalledList.size)
        assertEquals(UiState.Success(emptyList<Currency>()), communications.stateCalledList[0])

        assertEquals(0, communications.currenciesList.size)
        assertEquals(0, communications.timesShowList)

        interactor.changeExpectedResult(CurrencyResult.Error("No Internet Connection"))
        viewModel.fetchData()

        assertEquals(true, communications.progressCalledList[2])
        assertEquals(1, interactor.fetchDataCalledList.size)

        assertEquals(4, communications.progressCalledList.size)
        assertEquals(false, communications.progressCalledList[3])

        assertEquals(2, communications.stateCalledList.size)
        assertEquals(UiState.Error("No Internet Connection"), communications.stateCalledList[1])
        assertEquals(0, communications.timesShowList)

        viewModel.init(isFirstRun = false)
        assertEquals(4, communications.progressCalledList.size)
        assertEquals(2, communications.stateCalledList.size)
        assertEquals(0, communications.timesShowList)
    }

    private class TestCurrencyCommunications : CurrencyCommunications {

        val progressCalledList = mutableListOf<Boolean>()
        val stateCalledList = mutableListOf<UiState>()
        var timesShowList = 0
        val currenciesList = mutableListOf<UiState>()

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
    }

    private class TestCurrencyInteractor : CurrencyInteractor {

        private val result: CurrencyResult = CurrencyResult.Success()

        val initCalledList = mutableListOf<CurrencyResult>()
        val fetchDataCalledList = mutableListOf<CurrencyResult>()

        fun changeExpectedResult(newResult: CurrencyResult) {
            result = newResult
        }

        override fun init(): CurrencyResult {
            initCalledList.add(result)
            return result
        }

        override suspend fun fetchData() {
            fetchDataCalledList.add(result)
            return result
        }
    }
}