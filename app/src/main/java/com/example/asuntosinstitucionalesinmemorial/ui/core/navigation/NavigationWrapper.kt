package com.example.asuntosinstitucionalesinmemorial.ui.core.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.asuntosinstitucionalesinmemorial.ui.editstorage.EditStorageScreen
import com.example.asuntosinstitucionalesinmemorial.ui.events.EventDetailScreen
import com.example.asuntosinstitucionalesinmemorial.ui.events.EventGuestsScreen
import com.example.asuntosinstitucionalesinmemorial.ui.events.EventsScreen
import com.example.asuntosinstitucionalesinmemorial.ui.home.ButtonAction
import com.example.asuntosinstitucionalesinmemorial.ui.home.HomeScreen
import com.example.asuntosinstitucionalesinmemorial.ui.materialstorage.MaterialStorageScreen
import com.example.asuntosinstitucionalesinmemorial.ui.regalosstorage.RegalosStorageScreen
import com.example.asuntosinstitucionalesinmemorial.ui.storagedetail.StorageDetailScreen

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()

    Scaffold(bottomBar = { NavigationBar(navController = navController) }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Home,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<Home> { HomeScreen() }
            composable<RegalosStorage> {
                RegalosStorageScreen(
                    goToDetail = {
                        navController.navigate(
                            StorageDetail(
                                objeto = it,
                                type = ButtonAction.REGALOS
                            )
                        )
                    },
                    goToEdit = { navController.navigate(EditStorage) }
                )
            }
            composable<MaterialStorage> {
                MaterialStorageScreen(
                    goToDetail = {
                        navController.navigate(
                            StorageDetail(
                                objeto = it,
                                type = ButtonAction.MATERIAL
                            )
                        )
                    })
            }
            composable<StorageDetail> { navBackStackEntry ->
                val detail = navBackStackEntry.toRoute<StorageDetail>()
                StorageDetailScreen(
                    objeto = detail.objeto,
                    type = detail.type,
                    navigateBack = { navController.popBackStack() })
            }
            composable<EditStorage> { EditStorageScreen() }
            composable<Events> {
                EventsScreen(
                    goToEventDetail = { navController.navigate(EventDetail(event = it)) },
                    goToEventGuests = { navController.navigate(EventGuests(event = it)) }
                )
            }
            composable<EventDetail> { navBackStackEntry ->
                val event = navBackStackEntry.toRoute<EventDetail>()
                EventDetailScreen(
                    event = event.event
                )
            }
            composable<EventGuests> { navBackStackEntry ->
                val event = navBackStackEntry.toRoute<EventGuests>()
                EventGuestsScreen(
                    event = event.event
                )
            }
        }
    }
}