package com.example.asuntosinstitucionalesinmemorial.ui.protocolstorage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProtocolStorageScreen(
    protocolStorageViewModel: ProtocolStorageViewModel = koinViewModel(),
    navigateBack: () -> Unit
) {
    val uiState by protocolStorageViewModel.uiState.collectAsStateWithLifecycle()
    Scaffold { padding ->
        Column {
            Button(onClick = { navigateBack() }, modifier = Modifier.padding(padding)) {
                Text(text = "Navigate Back")
            }
            val buttonText = if (!uiState.internetConnection) {
                "There are not connection"
            } else {
                uiState.storage.material.toString()
            }
            Text(
                text = buttonText,
                modifier = Modifier.clickable { protocolStorageViewModel.downloadStorage() })
        }
    }
}