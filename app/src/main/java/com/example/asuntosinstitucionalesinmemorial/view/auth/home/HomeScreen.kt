package com.example.asuntosinstitucionalesinmemorial.view.auth.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(navigateToProtocolStorage: () -> Unit) {
    Scaffold() { padding ->
        Button(onClick = { navigateToProtocolStorage() }, modifier = Modifier.padding(padding)) {
            Text(text = "Navigate to Protocol Storage")
        }
    }
}