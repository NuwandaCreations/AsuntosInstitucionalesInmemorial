package com.example.asuntosinstitucionalesinmemorial.view.auth.protocolStorage

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ProtocolStorageScreen(navigateBack: () -> Unit) {
    Scaffold { padding ->
        Button(onClick = { navigateBack() }, modifier = Modifier.padding(padding)) {
            Text(text = "Navigate Back")
        }
    }
}