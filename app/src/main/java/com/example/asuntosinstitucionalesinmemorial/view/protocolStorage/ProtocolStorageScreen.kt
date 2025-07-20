package com.example.asuntosinstitucionalesinmemorial.view.protocolStorage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ProtocolStorageScreen(
    protocolStorageViewModel: ProtocolStorageViewModel = viewModel(),
    navigateBack: () -> Unit
) {
    val uiState by protocolStorageViewModel.uiState.collectAsStateWithLifecycle()
    Scaffold { padding ->
        Column {
            Button(onClick = { navigateBack() }, modifier = Modifier.padding(padding)) {
                Text(text = "Navigate Back")
            }
            Text(text = uiState.storage, modifier = Modifier.clickable { protocolStorageViewModel.getStorage() })
        }
    }
}