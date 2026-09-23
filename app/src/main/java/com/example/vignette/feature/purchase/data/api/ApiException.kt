package com.example.vignette.feature.purchase.data.api

sealed class ApiException : Exception() {

    class EndpointNotFoundException : ApiException()

    class InvalidMissingOrderException : ApiException()
}
