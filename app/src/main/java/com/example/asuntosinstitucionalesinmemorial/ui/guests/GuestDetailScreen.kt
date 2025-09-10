package com.example.asuntosinstitucionalesinmemorial.ui.guests

import androidx.compose.runtime.Composable
import com.example.asuntosinstitucionalesinmemorial.ui.events.EventsViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun GuestDetailScreen(
    eventsViewModel: EventsViewModel = koinViewModel(),
    guest: String
) {
}