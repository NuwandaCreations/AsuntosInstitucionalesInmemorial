package com.example.asuntosinstitucionalesinmemorial.ui.guests

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.asuntosinstitucionalesinmemorial.R
import com.example.asuntosinstitucionalesinmemorial.domain.model.InvitadosRelevo
import com.example.asuntosinstitucionalesinmemorial.ui.core.components.MyTextField
import com.example.asuntosinstitucionalesinmemorial.ui.events.EventsViewModel
import com.example.asuntosinstitucionalesinmemorial.ui.theme.Typography
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RelevoGuestsScreen(
    eventsViewModel: EventsViewModel = koinViewModel(),
    goToGuestDetail: (String) -> Unit,
    event: String
) {
    val uiState by eventsViewModel.uiState.collectAsState()
    var searchText by rememberSaveable { mutableStateOf("") }
    var expandedMenu by rememberSaveable { mutableStateOf(false) }
    var categoriaMenu by rememberSaveable { mutableStateOf("Todos") }
    var selectedGuest = InvitadosRelevo()

    eventsViewModel.getRelevoGuests(event)

    Scaffold(
        containerColor = colorResource(R.color.onPrimaryBackground),
    ) { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (uiState.isDialogShown) {
                CreateRelevoDialog(
                    guest = selectedGuest,
                    confirmAction = {
                        selectedGuest.accedido = true
                        eventsViewModel.setRelevoGuestFirestore(event, selectedGuest)
                        eventsViewModel.showDialog(false)
                    },
                    denyAction = {
                        selectedGuest.accedido = false
                        eventsViewModel.setRelevoGuestFirestore(event, selectedGuest)
                        eventsViewModel.showDialog(false)
                    },
                    dismissAction = {
                        eventsViewModel.showDialog(false)
                    }
                )
            }
            Text(
                text = stringResource(R.string.guests_screen_title),
                modifier = Modifier.padding(top = 10.dp),
                style = Typography.titleMedium
            )

            MyTextField(
                searchText = searchText,
                placeholderText = stringResource(R.string.placeholder_evento),
                expandedMenu = expandedMenu,
                invitadosRelevo = uiState.invitadosRelevo,
                onExpandedMenu = { expandedMenu = it },
                onSearchText = { searchText = it },
                onCategoriaMenu = { categoriaMenu = it }
            )

            LazyColumn(
                flingBehavior = ScrollableDefaults.flingBehavior(),
                state = rememberLazyListState(),
            ) {
                items(uiState.invitadosRelevo) { invitado ->
                    if (categoriaMenu == stringResource(R.string.all) || categoriaMenu == invitado.observaciones) {
                        if (searchText.isEmpty() || invitado.nombre
                                .contains(searchText, ignoreCase = true)
                        ) {
                            ElevatedCard(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp, 5.dp)
                                    .combinedClickable(
                                        onClick = {
                                            selectedGuest = invitado
                                            eventsViewModel.showDialog(true)
                                        },
                                        onLongClick = {
                                            selectedGuest = invitado
                                            goToGuestDetail(invitado.nombre)
                                        }
                                    )
                                    .border(
                                        width = 0.6.dp,
                                        color = colorResource(R.color.white),
                                        shape = RoundedCornerShape(7.dp) // Debe coincidir con el shape del card
                                    ),
                                colors = CardDefaults.elevatedCardColors(
                                    containerColor = if (invitado.accedido) colorResource(R.color.onSuccess) else colorResource(
                                        R.color.onPrimary
                                    ),
                                    contentColor = colorResource(R.color.white)
                                ),
                                shape = RoundedCornerShape(7.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(
                                        modifier = Modifier.weight(3f),
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        val textNombre =
                                            if (invitado.empleo.isEmpty()) invitado.nombre else "${invitado.empleo} ${invitado.nombre}"
                                        Text(
                                            text = textNombre,
                                            modifier = Modifier
                                                .padding(
                                                    horizontal = 15.dp
                                                )
                                        )
                                        Row(
                                            modifier = Modifier
                                                .padding(
                                                    horizontal = 15.dp
                                                )
                                        ) {
                                            val textVisita =
                                                if (invitado.visita1.isNotEmpty()) stringResource(R.string.first_visit)
                                                else if (invitado.visita2.isNotEmpty()) stringResource(
                                                    R.string.second_visit
                                                )
                                                else stringResource(R.string.no_visit)
                                            Text(
                                                text = textVisita,
                                                modifier = Modifier.padding(horizontal = 2.dp),
                                                color = colorResource(R.color.white_transparent)
                                            )
                                            val textActo =
                                                if (invitado.patio.isNotEmpty()) stringResource(R.string.yard_relevo)
                                                else stringResource(R.string.garden_relevo)
                                            Text(
                                                text = textActo,
                                                modifier = Modifier.padding(horizontal = 2.dp),
                                                color = colorResource(R.color.white_transparent)
                                            )
                                        }

                                        val textVino =
                                            if (invitado.vino.isNotEmpty()) stringResource(R.string.agape_access)
                                            else stringResource(R.string.agape_deny)
                                        Text(
                                            text = textVino,
                                            modifier = Modifier.padding(start = 17.dp, end = 7.dp, bottom = 5.dp),
                                            color = colorResource(R.color.white_transparent)
                                        )
                                    }
                                    if (invitado.foto.isNotEmpty()) {
                                        AsyncImage(
                                            model = invitado.foto,
                                            contentDescription = "photo",
                                            contentScale = ContentScale.Fit,
                                            modifier = Modifier
                                                .size(90.dp)
                                                .padding(7.dp)
                                                .weight(1f)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CreateRelevoDialog(
    guest: InvitadosRelevo,
    confirmAction: () -> Unit,
    denyAction: () -> Unit,
    dismissAction: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { dismissAction() },
        confirmButton = {
            TextButton(onClick = { confirmAction() }) {
                Text("SI", color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = { denyAction() }) {
                Text("NO", color = Color.White)
            }
        },
        title = {
            Text(text = "¿" + guest.nombre + stringResource(R.string.guest_dialog_title))
        },
        text = {
            Text(text = stringResource(R.string.guest_dialog_text))
        },
        containerColor = colorResource(R.color.onPrimary),
        titleContentColor = colorResource(R.color.white),
        textContentColor = colorResource(R.color.white)
    )
}