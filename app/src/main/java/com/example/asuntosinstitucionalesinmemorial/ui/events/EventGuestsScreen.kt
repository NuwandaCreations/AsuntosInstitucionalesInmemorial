package com.example.asuntosinstitucionalesinmemorial.ui.events

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun EventGuestsScreen(
    eventsViewModel: EventsViewModel = koinViewModel(),
    event: String,
    navController: NavController
) {

}