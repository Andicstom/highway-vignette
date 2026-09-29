package com.example.vignette.core.ui

sealed interface UiEvent {
    data object Navigate : UiEvent

    data object ShowErrorToast : UiEvent

    data object ShowCountiesNotNeighboursToast : UiEvent
}
