package com.example.asuntosinstitucionalesinmemorial.ui.events

import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.asuntosinstitucionalesinmemorial.R
import com.example.asuntosinstitucionalesinmemorial.ui.core.components.Card
import com.example.asuntosinstitucionalesinmemorial.ui.core.components.EventsCard
import com.example.asuntosinstitucionalesinmemorial.ui.theme.Typography
import org.koin.compose.viewmodel.koinViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun EventsScreen(
    eventsViewModel: EventsViewModel = koinViewModel(),
    goToEventDetail: (String) -> Unit,
    goToEventGuests: (String) -> Unit,
    goToRelevoGuests: (String) -> Unit,
    goToCreateEvent: () -> Unit
) {
    val uiState by eventsViewModel.uiState.collectAsStateWithLifecycle()

    eventsViewModel.getEventsFirestore()

    Scaffold(
        containerColor = colorResource(R.color.onPrimaryBackground),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                goToCreateEvent()
            }) {
                Icon(Icons.Default.Add, null)
            }
        },
        floatingActionButtonPosition = FabPosition.EndOverlay
    ) { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.events_screen_title),
                modifier = Modifier.padding(top = 10.dp),
                style = Typography.titleMedium
            )
            LazyColumn(
                flingBehavior = ScrollableDefaults.flingBehavior(),
                state = rememberLazyListState(),
            ) {
                items(uiState.events) { evento ->
                    EventsCard(
                        name = evento.nombre.toString(),
                        date = evento.fecha.toString(),
                        photoUrl = evento.imagen.toString(),
                        onClick = {
                            if (evento.esRelevoGuardia == true) {
                                goToRelevoGuests(evento.id.toString())
                            } else {
                                goToEventGuests(evento.id.toString())

                            }
                        },
                        onLongClick = { goToEventDetail(evento.id.toString()) }
                    )
                }
            }
        }
    }

}