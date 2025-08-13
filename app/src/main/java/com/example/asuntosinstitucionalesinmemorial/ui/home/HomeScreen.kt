package com.example.asuntosinstitucionalesinmemorial.ui.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.asuntosinstitucionalesinmemorial.ui.core.navigation.NavigationBar
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = koinViewModel(),
    navigateBack: () -> Unit
) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    homeViewModel.onIsLoadingChanged(true)
    Scaffold { padding ->
        Button(
            onClick = {
                navigateBack()
                homeViewModel.onIsLoadingChanged(false)
            },
            modifier = Modifier.padding(padding)
        ) {
            Text(text = "Navigate to Protocol Storage ${uiState.isLoading}")
        }
    }
}