package com.blipblipcode.squaddemo.ui.start.state

sealed class SplashUiState {
    data object Loading : SplashUiState()
    data object RequireUpdate : SplashUiState()
    data object Navigate : SplashUiState()
}