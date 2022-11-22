package com.github.andreyzim.currencyapp.domain

abstract class DomainException: IllegalStateException()

class NoInternetConnectionException() : DomainException()

class ServiceNotAvailableException() : DomainException()