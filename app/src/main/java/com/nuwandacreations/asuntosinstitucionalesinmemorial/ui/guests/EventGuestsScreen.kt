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
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.model.Invitados
import com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.core.components.MyTextField
import com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.events.EventsViewModel
import com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.theme.Typography
import com.nuwandacreations.asuntosinstitucionalesinmemorial.util.hexToColorInt
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun EventGuestsScreen(
    eventsViewModel: EventsViewModel = koinViewModel(),
    goToGuestDetail: (String) -> Unit,
    event: String
) {
    val uiState by eventsViewModel.uiState.collectAsState()
    var searchText by rememberSaveable { mutableStateOf("") }
    var expandedMenu by rememberSaveable { mutableStateOf(false) }
    var categoriaMenu by rememberSaveable { mutableStateOf("Todos") }
    var selectedGuest = Invitados()
//TODO LOS INVITADOS DEL RELEVO AQUÍ NO FUNCIONA, HAY QUE HACER OTRA SCREEN O VER COMO ENCAJARLO
    eventsViewModel.getEventGuests(event)
    eventsViewModel.getGuestsPhotos()

    Scaffold(
        containerColor = colorResource(R.color.onPrimaryBackground),
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
                    guest = selectedGuest,
                    confirmAction = {
                        selectedGuest.accedido = true
                        eventsViewModel.setGuestFirestore(event, selectedGuest)
                        eventsViewModel.showDialog(false)
                    },
                    denyAction = {
                        selectedGuest.accedido = false
                        eventsViewModel.setGuestFirestore(event, selectedGuest)
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
                onExpandedMenu = { expandedMenu = it },
                onSearchText = { searchText = it },
                onCategoriaMenu = { categoriaMenu = it }
            )

            LazyColumn(
                flingBehavior = ScrollableDefaults.flingBehavior(),
                state = rememberLazyListState(),
            ) {
                items(uiState.invitados) { invitado ->
                    eventsViewModel.searchPhoto(event, invitado)
                    if (categoriaMenu == stringResource(R.string.all) || categoriaMenu == invitado.grupo) {
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
                                    containerColor = if (invitado.accedido) colorResource(R.color.onSuccessTransparent) else colorResource(
                                        R.color.onPrimaryTransparent
                                    ),
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
                                            .background(Color(hexToColorInt(invitado.color) ?: 0))
                                    )
                                    Column(
                                        modifier = Modifier.weight(3f),
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            text = invitado.nombre,
                                            modifier = Modifier.padding(
                                                start = 15.dp,
                                                end = 15.dp,
                                                top = 5.dp,
                                            )
                                        )
                                        val textGrupo =
                                            if (invitado.cargo.isEmpty()) invitado.grupo else "${invitado.grupo} - ${invitado.cargo}"
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
fun CreateDialog(
    guest: Invitados,
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