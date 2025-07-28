package com.example.asuntosinstitucionalesinmemorial.ui.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel() : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    fun onIsLoadingChanged(isLoading: Boolean) {
        _uiState.update {
            it.copy( isLoading = isLoading)
        }
    }
}

data class HomeUiState(
    val isLoading: Boolean = false
)