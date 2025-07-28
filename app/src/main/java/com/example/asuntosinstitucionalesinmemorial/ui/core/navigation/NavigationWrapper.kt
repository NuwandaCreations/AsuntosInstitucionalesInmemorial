package com.example.asuntosinstitucionalesinmemorial.ui.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.asuntosinstitucionalesinmemorial.ui.home.HomeScreen
import com.example.asuntosinstitucionalesinmemorial.ui.protocolstorage.ProtocolStorageScreen

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Home) {
        composable<Home> { HomeScreen(navigateToProtocolStorage = { navController.navigate(ProtocolStorage) }) }
        composable<ProtocolStorage> { ProtocolStorageScreen(navigateBack = { navController.popBackStack() }) }
    }
}