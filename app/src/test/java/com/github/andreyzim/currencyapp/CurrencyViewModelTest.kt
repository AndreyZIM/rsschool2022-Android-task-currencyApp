package com.github.andreyzim.currencyapp

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.github.andreyzim.currencyapp.domain.CurrencyInteractor
import com.github.andreyzim.currencyapp.domain.CurrencyResult
import com.github.andreyzim.currencyapp.domain.CurrencyUiMapper
import com.github.andreyzim.currencyapp.presentation.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CurrencyViewModelTest : BaseTest() {

    private lateinit var communications: TestCurrencyCommunications
    private lateinit var interactor: TestCurrencyInteractor
    private lateinit var manageResources: TestManageResources
    private lateinit var viewModel: CurrencyViewModel

    @Before
    fun init() {
        communications = TestCurrencyCommunications()
        interactor = TestCurrencyInteractor()
        manageResources = TestManageResources()
        viewModel = CurrencyViewModel(
            HandleCurrencyRequest.Base(
                TestDispatchersList(),
                communications,
                CurrencyResultMapper(communications, CurrencyUiMapper())
            ), manageResources, communications, interactor
        )
    }

    @Test
    fun `test init and re-init`() = runBlocking {

        interactor.changeExpectedResult(CurrencyResult.Success())
        viewModel.fetchCurrency()


        assertEquals(true, communications.progressCalledList[0])
        assertEquals(2, communications.progressCalledList.size)
        assertEquals(false, communications.progressCalledList[1])

        assertEquals(1, communications.stateCalledList.size)
        assertEquals(true, (communications.stateCalledList[0] is UiState.Success))

        assertEquals(0, communications.currenciesList.size)
        assertEquals(1, communications.timesShowList)

        interactor.changeExpectedResult(CurrencyResult.Failure("No Internet Connection"))
        viewModel.fetchCurrency()

        assertEquals(true, communications.progressCalledList[2])
        assertEquals(2, interactor.fetchDataCalledList.size)

        assertEquals(4, communications.progressCalledList.size)
        assertEquals(false, communications.progressCalledList[3])

        assertEquals(2, communications.stateCalledList.size)
        assertEquals(UiState.Error("No Internet Connection"), communications.stateCalledList[1])
        assertEquals(1, communications.timesShowList)
    }

    private class TestManageResources : ManageResources {

        private var string: String = ""

        fun makeExpectedResult(result: String) {
            string = result
        }

        override fun string(id: Int): String = string
    }

    private class TestCurrencyInteractor : CurrencyInteractor {

        private var result: CurrencyResult = CurrencyResult.Success()
        val fetchDataCalledList = mutableListOf<CurrencyResult>()

        fun changeExpectedResult(newResult: CurrencyResult) {
            result = newResult
        }

        override fun fetchCurrency(): CurrencyResult {
            fetchDataCalledList.add(result)
            return result
        }
    }

    private class TestDispatchersList(
        private val coroutineDispatcher: CoroutineDispatcher = TestCoroutineDispatcher()
    ) : DispatchersList {

        override fun io(): CoroutineDispatcher = coroutineDispatcher
        override fun ui(): CoroutineDispatcher = coroutineDispatcher
    }
}