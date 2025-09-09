package com.example.asuntosinstitucionalesinmemorial.ui.events

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.asuntosinstitucionalesinmemorial.R
import com.example.asuntosinstitucionalesinmemorial.ui.core.components.BasicDialog
import com.example.asuntosinstitucionalesinmemorial.ui.core.components.MyButton
import com.example.asuntosinstitucionalesinmemorial.ui.theme.Typography
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun EventDetailScreen(
    eventsViewModel: EventsViewModel = koinViewModel(),
    event: String
) {
    val uiState by eventsViewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    eventsViewModel.getEventByIdFirestore(event)

    Scaffold(
        containerColor = colorResource(R.color.onPrimaryBackground)
    ) { padding ->
        if (uiState.isDialogShown) {
            SetGuestsFromStorage(
                confirmAction = {
                    eventsViewModel.getEventGuestsStorage(
                        uiState.event?.id ?: "",
                        uiState.event?.esRelevoGuardia ?: false
                    )
                    eventsViewModel.showDialog(false)
                },
                dismissAction = {
                    eventsViewModel.showDialog(false)
                }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            uiState.event?.let { event ->
                Text(
                    text = event.nombre ?: "",
                    modifier = Modifier.padding(horizontal = 15.dp),
                    style = Typography.titleMedium,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "${event.lugar} el ${event.fecha}",
                    modifier = Modifier.padding(start = 15.dp, end = 15.dp, bottom = 25.dp),
                    color = colorResource(R.color.white_transparent),
                    textAlign = TextAlign.Center
                )
                AsyncImage(
                    model = event.imagen,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .sizeIn(
                            maxWidth = 300.dp,
                            maxHeight = 300.dp,
                            minWidth = 200.dp,
                            minHeight = 200.dp
                        )
                        .padding(7.dp)
                )
                Text(
                    text = event.descripcion ?: "",
                    modifier = Modifier.padding(
                        start = 15.dp,
                        end = 15.dp,
                        top = 25.dp,
                        bottom = 15.dp
                    ),
                    textAlign = TextAlign.Justify,
                    color = Color.White
                )
                MyButton(text = "Subir invitados desde JSON") {
                    eventsViewModel.showDialog(true)
                }
            }
        }
    }
}

@Composable
fun SetGuestsFromStorage(confirmAction: () -> Unit, dismissAction: () -> Unit) {
    BasicDialog(
        title = stringResource(R.string.event_detail_dialog_title),
        text = stringResource(R.string.event_detail_dialog_text),
        confirmButton = {
            confirmAction()
        },
        dismissButton = {
            dismissAction()
        }
    )
}
