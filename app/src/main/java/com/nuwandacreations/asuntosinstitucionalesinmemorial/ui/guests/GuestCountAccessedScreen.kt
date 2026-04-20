package com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.guests

import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nuwandacreations.asuntosinstitucionalesinmemorial.R
import com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.events.EventsViewModel
import com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.theme.Typography
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun GuestCountAccessedScreen(
    eventsViewModel: EventsViewModel = koinViewModel(),
    event: String,
    isRelevoGuardia: Boolean = false,
    goToEventGuestsScreen: (String, Boolean, String) -> Unit,
) {
    val uiState by eventsViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        eventsViewModel.getEventGuests(event = event, esRelevoGuardia = isRelevoGuardia)
    }

    val totalAccessed = if (isRelevoGuardia) {
        uiState.invitadosRelevo.count { it.accedido == true } to uiState.invitadosRelevo.size
    } else {
        uiState.invitados.count { it.accedido == true } to uiState.invitados.size
    }

    val accessedGuestsPerGroup: Map<String, Pair<Int, Int>> = if (isRelevoGuardia) {
        uiState.invitadosRelevo
            .groupBy { it.observaciones }
            .mapValues { (_, guests) ->
                val total = guests.size
                val accessed = guests.count { it.accedido == true }
                accessed to total
            }
    } else {
        uiState.invitados
            .groupBy { it.grupo }
            .mapValues { (_, guests) ->
                val total = guests.size
                val accessed = guests.count { it.accedido == true }
                accessed to total
            }
    }

    Scaffold(
        containerColor = colorResource(R.color.onPrimaryBackground),
    ) { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(padding)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.guests_accessed_screen_title),
                style = Typography.titleMedium
            )

            LazyColumn(
                flingBehavior = ScrollableDefaults.flingBehavior(),
                state = rememberLazyListState(),
                modifier = Modifier.padding(top = 10.dp)
            ) {
                item {
                    GuestElevatedCard(
                        onClick = {
                            goToEventGuestsScreen(event, isRelevoGuardia, "Todos")
                        },
                        onLongClick = {},
                        guestName = "TOTAL",
                        guestPosition = "",
                        guestCompany = "Han accedido: ${totalAccessed.first} / ${totalAccessed.second}",
                        guestAccessed = if (totalAccessed.first == totalAccessed.second) true else null,
                        guestColor = "",
                        guestPhoto = ""
                    )
                }
                items(accessedGuestsPerGroup.entries.toList()) { (group, counts) ->
                    GuestElevatedCard(
                        onClick = {
                            goToEventGuestsScreen(event, isRelevoGuardia, group)
                        },
                        onLongClick = {},
                        guestName = group,
                        guestPosition = "",
                        guestCompany = "Han accedido: ${counts.first} / ${counts.second}",
                        guestAccessed = (counts.first == counts.second),
                        guestColor = "",
                        guestPhoto = ""
                    )
                }
            }
        }
    }
}