package com.github.andreyzim.currencyapp.presentation

import android.widget.TextView

data class CurrencyUi(private val rate: String, private val base: String) {

    fun map(head: TextView, subTitle: TextView) {
        head.text = base
        subTitle.text = rate
    }
}
