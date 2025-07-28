package com.example.asuntosinstitucionalesinmemorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.asuntosinstitucionalesinmemorial.ui.theme.AsuntosInstitucionalesInmemorialTheme
import com.example.asuntosinstitucionalesinmemorial.ui.core.navigation.NavigationWrapper

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AsuntosInstitucionalesInmemorialTheme {
                NavigationWrapper()
            }
        }
    }
}