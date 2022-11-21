package com.github.andreyzim.currencyapp.presentation

interface UiState {

    class Success() : UiState

    data class Error(private val message: String): UiState
}