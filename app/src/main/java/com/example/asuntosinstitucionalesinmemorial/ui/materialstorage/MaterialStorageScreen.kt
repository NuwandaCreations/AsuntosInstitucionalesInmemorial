package com.example.asuntosinstitucionalesinmemorial.ui.materialstorage

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun MaterialStorageScreen(navController: NavController) {
    Scaffold { padding ->
        Text(text = "Material Storage", modifier = Modifier.padding(padding))
    }
}