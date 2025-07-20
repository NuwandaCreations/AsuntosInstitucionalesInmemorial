package com.example.asuntosinstitucionalesinmemorial.view.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.asuntosinstitucionalesinmemorial.view.home.HomeScreen
import com.example.asuntosinstitucionalesinmemorial.view.protocolStorage.ProtocolStorageScreen

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Home) {
        composable<Home> { HomeScreen(navigateToProtocolStorage = { navController.navigate(ProtocolStorage) }) }
        composable<ProtocolStorage> { ProtocolStorageScreen(navigateBack = { navController.popBackStack() }) }
    }
}