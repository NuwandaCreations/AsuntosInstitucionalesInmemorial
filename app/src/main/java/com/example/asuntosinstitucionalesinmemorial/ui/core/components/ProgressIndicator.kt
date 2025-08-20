package com.example.asuntosinstitucionalesinmemorial.ui.core.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.asuntosinstitucionalesinmemorial.R

@Composable
fun ProgressIndicator() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 25.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        CircularProgressIndicator(
            strokeWidth = 10.dp,
            color = colorResource(R.color.onPrimary),
            trackColor = colorResource(R.color.white),
            modifier = Modifier.size(70.dp)
        )
    }
}