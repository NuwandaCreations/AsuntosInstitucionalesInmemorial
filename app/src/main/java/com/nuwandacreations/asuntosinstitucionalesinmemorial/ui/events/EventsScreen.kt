package com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.events

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.firebase.Timestamp
import com.nuwandacreations.asuntosinstitucionalesinmemorial.R
import com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.core.components.EventsCard
import com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.theme.Typography
import com.nuwandacreations.asuntosinstitucionalesinmemorial.util.dateToTimestamp
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventsScreen(
    eventsViewModel: EventsViewModel = koinViewModel(),
    goToEventDetail: (String) -> Unit,
    goToEventGuests: (String) -> Unit,
    goToRelevoGuests: (String) -> Unit,
    goToCreateEvent: () -> Unit
) {
    val uiState by eventsViewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        eventsViewModel.getEventsFirestore()
    }

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    val nowTimestamp = Timestamp.now()
    val listStartIndex = uiState.events.indexOfFirst { event ->
        if (event.fecha.isNullOrEmpty()) return@indexOfFirst false
        else {
            val eventTimestamp = dateToTimestamp(event.fecha)
            eventTimestamp?.let { it >= nowTimestamp } ?: false
        }
    }.takeIf { it >= 0 } ?: 0

    val listState = rememberLazyListState()
    LaunchedEffect(listStartIndex) {
        listState.scrollToItem(listStartIndex)
    }

    LaunchedEffect(uiState.events) {
        uiState.events.forEach { evento ->
            if (evento.imagen.isNullOrEmpty()) {
                eventsViewModel.getEventPhotoByIdStorage(evento)
            }
        }
    }

    Scaffold(
        containerColor = colorResource(R.color.onPrimaryBackground),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                goToCreateEvent()
            }) {
                Icon(Icons.Default.Add, null)
            }
        },
        floatingActionButtonPosition = FabPosition.EndOverlay,
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 90.dp, horizontal = 20.dp)
                .alpha(0.7f)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_rinf1),
                contentDescription = "rinf1",
                Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            var bottomPadding = 1.dp
            if (!isLandscape) {
                bottomPadding = 70.dp
                Text(
                    text = stringResource(R.string.events_screen_title),
                    style = Typography.titleMedium
                )
            }
            LazyRow(
                state = listState,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(700.dp),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = bottomPadding)
            ) {
                items(uiState.events) { evento ->
                    EventsCard(
                        name = evento.nombre ?: "",
                        date = evento.fecha ?: "",
                        place = evento.lugar ?: "",
                        photoUrl = evento.imagen ?: "",
                        onClick = {
                            if (evento.esRelevoGuardia) {
                                goToRelevoGuests(evento.id.toString())
                            } else {
                                goToEventGuests(evento.id.toString())
                            }
                        },
                        onLongClick = {
                            evento.id?.let {
                                goToEventDetail(it)
                            }
                        }
                    )
                }
            }
        }
    }
}