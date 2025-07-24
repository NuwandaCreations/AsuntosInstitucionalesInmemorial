package com.example.asuntosinstitucionalesinmemorial.view.home

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.asuntosinstitucionalesinmemorial.di.provideRetrofit
import com.example.asuntosinstitucionalesinmemorial.view.core.network.GoogleDriveService

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = viewModel(), navigateToProtocolStorage: () -> Unit) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    homeViewModel.onIsLoadingChanged(true)
    homeViewModel.downloadDriveFile()
    Scaffold() { padding ->
        Button(
            onClick = {
                navigateToProtocolStorage()
                homeViewModel.onIsLoadingChanged(false)
            },
            modifier = Modifier.padding(padding)
        ) {
            Text(text = "Navigate to Protocol Storage ${uiState.isLoading}")
        }
    }
}