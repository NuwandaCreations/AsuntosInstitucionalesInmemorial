package com.example.asuntosinstitucionalesinmemorial.ui.editstorage

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun EditStorageScreen() {
    Scaffold { padding ->
        Text(text = "Edit storage screen", modifier = Modifier.padding(padding))
    }
}