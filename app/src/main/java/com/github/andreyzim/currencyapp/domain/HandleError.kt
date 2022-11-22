package com.github.andreyzim.currencyapp.domain

import com.github.andreyzim.currencyapp.R
import com.github.andreyzim.currencyapp.presentation.ManageResources

interface HandleError {

    fun handle(e: Exception): String

    class Base(
        private val manageResources: ManageResources
    ) : HandleError {

        override fun handle(e: Exception): String = manageResources.string(
            when (e) {
                is NoInternetConnectionException -> R.string.no_internet_message
                else -> R.string.service_is_unavailable
            }
        )
    }
}