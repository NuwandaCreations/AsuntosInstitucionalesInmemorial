package com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.core.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.editstorage.EditStorageScreen
import com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.events.CreateEventScreen
import com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.events.EventDetailScreen
import com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.events.EventsScreen
import com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.guests.EventGuestsScreen
import com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.guests.GuestDetailScreen
import com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.home.ButtonAction
import com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.home.HomeScreen
import com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.materialstorage.MaterialStorageScreen
import com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.qrscanner.QrScannerScreen
import com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.regalosstorage.RegalosStorageScreen
import com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.storagedetail.StorageDetailScreen

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
                    goToEventGuests = { event, isRelevo ->
                        navController.navigate(EventGuests(event = event, esRelevo = isRelevo))
                    },
                    goToCreateEvent = { navController.navigate(CreateEvent) }
                )
            }
            composable<EventDetail> { navBackStackEntry ->
                val event = navBackStackEntry.toRoute<EventDetail>()
                EventDetailScreen(
                    event = event.event,
                    navigateBack = { navController.popBackStack() }
                )
            }
            composable<CreateEvent> {
                CreateEventScreen(
                    navigateBack = { navController.popBackStack() }
                )
            }
            composable<EventGuests> { navBackStackEntry ->
                val event = navBackStackEntry.toRoute<EventGuests>()
                EventGuestsScreen(
                    event = event.event,
                    goToQrScanner = {
                        navController.navigate(
                            QrScanner(
                                event = event.event,
                                esRelevo = false
                            )
                        )
                    },
                    goToGuestDetail = {
                        navController.navigate(
                            GuestDetail(
                                event = event.event,
                                guest = it,
                                esRelevo = false
                            )
                        )
                    }
                )
            }
            composable<QrScanner> { navBackStackEntry ->
                val event = navBackStackEntry.toRoute<QrScanner>()
                QrScannerScreen(
                    navigateBack = { navController.popBackStack() },
                    goToGuestDetail = {
                        navController.navigate(
                            GuestDetail(
                                event = event.event,
                                guest = it,
                                esRelevo = event.esRelevo
                            )
                        )
                    }
                )
            }
            composable<GuestDetail> { navBackStackEntry ->
                val detail = navBackStackEntry.toRoute<GuestDetail>()
                GuestDetailScreen(
                    event = detail.event,
                    guest = detail.guest,
                    esRelevo = detail.esRelevo,
                    navigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}