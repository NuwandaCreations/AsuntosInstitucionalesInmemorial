package com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.guests

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.nuwandacreations.asuntosinstitucionalesinmemorial.R
import com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.core.components.MyTextField
import com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.events.EventsViewModel
import com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.theme.Typography
import com.nuwandacreations.asuntosinstitucionalesinmemorial.util.Constants.Companion.AFIRMATIVE
import com.nuwandacreations.asuntosinstitucionalesinmemorial.util.Constants.Companion.NEGATIVE
import com.nuwandacreations.asuntosinstitucionalesinmemorial.util.Constants.Companion.SCANNER
import com.nuwandacreations.asuntosinstitucionalesinmemorial.util.hexToColorInt
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun EventGuestsScreen(
    eventsViewModel: EventsViewModel = koinViewModel(),
    goToGuestDetail: (String) -> Unit,
    goToQrScanner: () -> Unit,
    event: String,
    isRelevoGuardia: Boolean = false
) {
    val uiState by eventsViewModel.uiState.collectAsState()
    var searchText by rememberSaveable { mutableStateOf("") }
    var expandedMenu by rememberSaveable { mutableStateOf(false) }
    var categoriaMenu by rememberSaveable { mutableStateOf("Todos") }

    eventsViewModel.getEventGuests(event = event, esRelevoGuardia = isRelevoGuardia)
    eventsViewModel.getGuestsPhotos()

    Scaffold(
        containerColor = colorResource(R.color.onPrimaryBackground),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                goToQrScanner()
            }) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_qr_scanner),
                        contentDescription = null,
                        modifier = Modifier.size(50.dp)
                    )
                    Text(text = SCANNER)
                }
            }
        }
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
            modifier = Modifier.fillMaxWidth()
        ) {
            if (uiState.isDialogShown) {
                CreateDialog(
                    guestName = if (isRelevoGuardia) uiState.invitadoRelevoSelected.nombre else uiState.invitadoSelected.nombre,
                    confirmAction = {
                        eventsViewModel.setGuestAccess(
                            access = true,
                            isRelevo = isRelevoGuardia,
                            event = event
                        )
                        eventsViewModel.showDialog(false)
                    },
                    denyAction = {
                        eventsViewModel.setGuestAccess(
                            access = false,
                            isRelevo = isRelevoGuardia,
                            event = event
                        )
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
                invitados = uiState.invitados,
                invitadosRelevo = uiState.invitadosRelevo,
                onExpandedMenu = { expandedMenu = it },
                onSearchText = { searchText = it },
                onCategoriaMenu = { categoriaMenu = it }
            )

            LazyColumn(
                flingBehavior = ScrollableDefaults.flingBehavior(),
                state = rememberLazyListState(),
            ) {
                if (isRelevoGuardia) {
                    items(uiState.invitadosRelevo) { relevoGuest ->
                        if (relevoGuest.foto.isEmpty()) {
                            eventsViewModel.searchPhoto(
                                event = event,
                                isRelevo = true,
                                invitadoRelevo = relevoGuest
                            )
                        }
                        if (categoriaMenu == stringResource(R.string.all) || categoriaMenu == relevoGuest.observaciones) {
                            if (searchText.isEmpty() || relevoGuest.nombre
                                    .contains(searchText, ignoreCase = true)
                            ) {
                                val visitText =
                                    if (relevoGuest.visita1.isNotEmpty()) stringResource(R.string.first_visit)
                                    else if (relevoGuest.visita2.isNotEmpty()) stringResource(R.string.second_visit)
                                    else stringResource(R.string.no_visit)

                                val relevoText =
                                    if (relevoGuest.patio.isNotEmpty()) stringResource(R.string.yard_relevo)
                                    else if (relevoGuest.jardines.isNotEmpty()) stringResource(R.string.garden_relevo)
                                    else stringResource(R.string.no_relevo)

                                GuestElevatedCard(
                                    onClick = {
                                        eventsViewModel.selectGuest(
                                            invitadoRelevo = relevoGuest,
                                            isRelevo = true,
                                            isShortClick = true
                                        )
                                    },
                                    onLongClick = {
                                        eventsViewModel.selectGuest(
                                            invitadoRelevo = relevoGuest,
                                            isRelevo = true
                                        )
                                        goToGuestDetail(relevoGuest.nombre)
                                    },
                                    guestName = relevoGuest.nombre,
                                    guestPosition = "$visitText - $relevoText",
                                    guestCompany = relevoGuest.empleo,
                                    guestAccessed = relevoGuest.accedido,
                                    guestColor = relevoGuest.color,
                                    guestPhoto = relevoGuest.foto
                                )
                            }
                        }
                    }
                } else {
                    items(uiState.invitados) { guest ->
                        if (guest.foto.isEmpty()) {
                            eventsViewModel.searchPhoto(
                                event = event,
                                isRelevo = false,
                                invitado = guest
                            )
                        }
                        if (categoriaMenu == stringResource(R.string.all) || categoriaMenu == guest.grupo) {
                            if (searchText.isEmpty() || guest.nombre
                                    .contains(searchText, ignoreCase = true)
                            ) {
                                GuestElevatedCard(
                                    onClick = {
                                        eventsViewModel.selectGuest(
                                            invitado = guest,
                                            isRelevo = false,
                                            isShortClick = true
                                        )
                                    },
                                    onLongClick = {
                                        eventsViewModel.selectGuest(
                                            invitado = guest,
                                            isRelevo = false
                                        )
                                        goToGuestDetail(guest.nombre)
                                    },
                                    guestName = guest.nombre,
                                    guestPosition = guest.cargo,
                                    guestCompany = guest.grupo,
                                    guestAccessed = guest.accedido,
                                    guestColor = guest.color,
                                    guestPhoto = guest.foto
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GuestElevatedCard(
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    guestName: String,
    guestPosition: String,
    guestCompany: String,
    guestAccessed: Boolean?,
    guestColor: String,
    guestPhoto: String
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp, 5.dp)
            .combinedClickable(
                onClick = { onClick() },
                onLongClick = { onLongClick() }
            )
            .border(
                width = 0.6.dp,
                color = colorResource(R.color.white),
                shape = RoundedCornerShape(7.dp) // Debe coincidir con el shape del card
            ),
        colors = CardDefaults.elevatedCardColors(
            containerColor = when (guestAccessed) {
                true -> colorResource(R.color.onSuccessTransparent)
                false -> colorResource(R.color.onPrimaryTransparent)
                null -> colorResource(R.color.white_transparent)
            },
            contentColor = colorResource(R.color.white)
        ),
        shape = RoundedCornerShape(7.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(20.dp)
                    .fillMaxHeight()
                    .background(Color(hexToColorInt(guestColor) ?: 0))
            )
            Column(
                modifier = Modifier.weight(3f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = guestName,
                    modifier = Modifier.padding(
                        start = 15.dp,
                        end = 15.dp,
                        top = 5.dp,
                    )
                )

                val textGrupo = listOf(guestCompany, guestPosition).filter { it.isNotEmpty() }
                    .joinToString(" - ")
                Text(
                    text = textGrupo,
                    modifier = Modifier.padding(
                        start = 15.dp,
                        end = 15.dp,
                        bottom = 5.dp,
                    ),
                    color = colorResource(R.color.white_transparent)
                )
            }
            if (guestPhoto.isNotEmpty()) {
                AsyncImage(
                    model = guestPhoto,
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

@Composable
fun CreateDialog(
    guestName: String,
    confirmAction: () -> Unit,
    denyAction: () -> Unit,
    dismissAction: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { dismissAction() },
        confirmButton = {
            TextButton(onClick = { confirmAction() }) {
                Text(AFIRMATIVE, color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = { denyAction() }) {
                Text(NEGATIVE, color = Color.White)
            }
        },
        title = {
            Text(text = "¿" + guestName + stringResource(R.string.guest_dialog_title))
        },
        text = {
            Text(text = stringResource(R.string.guest_dialog_text))
        },
        containerColor = colorResource(R.color.onPrimary),
        titleContentColor = colorResource(R.color.white),
        textContentColor = colorResource(R.color.white)
    )
}