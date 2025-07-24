package com.example.asuntosinstitucionalesinmemorial.view.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asuntosinstitucionalesinmemorial.di.provideRetrofit
import com.example.asuntosinstitucionalesinmemorial.view.core.network.GoogleDriveService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel() : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    fun onIsLoadingChanged(isLoading: Boolean) {
        _uiState.update {
            it.copy( isLoading = isLoading)
        }
    }

    fun downloadDriveFile() {
        viewModelScope.launch {
            GoogleDriveService(provideRetrofit()).downloadDriveFile()
        }
    }
}

data class HomeUiState(
    val isLoading: Boolean = false
)