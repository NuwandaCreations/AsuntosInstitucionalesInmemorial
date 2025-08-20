package com.example.asuntosinstitucionalesinmemorial.ui.storagedetail

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun StorageDetailScreen(navigateBack: () -> Unit) {
    Scaffold { padding ->
        Text(text = "Storage Detail", modifier = Modifier.padding(padding))
    }
}