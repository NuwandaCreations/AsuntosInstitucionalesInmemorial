package com.example.asuntosinstitucionalesinmemorial.ui.guests

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.asuntosinstitucionalesinmemorial.R
import com.example.asuntosinstitucionalesinmemorial.ui.core.components.MyButton
import com.example.asuntosinstitucionalesinmemorial.ui.theme.Typography
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun GuestDetailScreen(
    guestDetailViewModel: GuestDetailViewModel = koinViewModel(),
    event: String,
    guest: String,
    esRelevo: Boolean
) {
    val uiState by guestDetailViewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    guestDetailViewModel.getGuestFirestore(event, guest, esRelevo)

    Scaffold(
        containerColor = colorResource(R.color.onPrimaryBackground)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (uiState.isDialogShown) {
                CreateDialog(
                    guest = uiState.invitado,
                    confirmAction = {
                        uiState.invitado.accedido = true
                        guestDetailViewModel.setGuestFirestore(event, uiState.invitado)
                        guestDetailViewModel.showDialog(false)
                    },
                    denyAction = {
                        uiState.invitado.accedido = false
                        guestDetailViewModel.setGuestFirestore(event, uiState.invitado)
                        guestDetailViewModel.showDialog(false)
                    },
                    dismissAction = {
                        guestDetailViewModel.showDialog(false)
                    }
                )
            }
            uiState.invitado.let { invitado ->
                Text(
                    text = invitado.nombre,
                    modifier = Modifier.padding(horizontal = 15.dp),
                    style = Typography.titleMedium,
                    textAlign = TextAlign.Center
                )

                AsyncImage(
                    model = invitado.foto,
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
                    text = invitado.grupo,
                    modifier = Modifier.padding(horizontal = 15.dp),
                    style = Typography.titleMedium,
                    textAlign = TextAlign.Center
                )

                if (invitado.cargo.isNotEmpty()) {
                    Text(
                        text = invitado.cargo,
                        modifier = Modifier.padding(horizontal = 15.dp),
                        style = Typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                }

                if (invitado.vehiculo.isNotEmpty()) {
                    Text(
                        text = "Vehículo: ${invitado.vehiculo}",
                        modifier = Modifier.padding(horizontal = 15.dp),
                        style = Typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                }

                if (invitado.accedido) {
                    Text(
                        text = "El invitado ha accedido",
                        modifier = Modifier.padding(horizontal = 15.dp),
                        style = Typography.titleMedium,
                        textAlign = TextAlign.Center,
                        color = Color.Green
                    )
                } else {
                    Text(
                        text = "El invitado no ha llegado",
                        modifier = Modifier.padding(horizontal = 15.dp),
                        style = Typography.titleMedium,
                        textAlign = TextAlign.Center,
                        color = Color.Red
                    )
                }


                MyButton(text = "Actualizar acceso invitado") {
                    guestDetailViewModel.showDialog(true)
                }
//            val invitado =
//                if (esRelevo) uiState.invitadoRelevo.toString() else uiState.invitado.toString()
//
//            Text(
//                text = text,
//                modifier = Modifier.padding(start = 15.dp, end = 15.dp, bottom = 25.dp),
//                color = colorResource(R.color.white_transparent),
//            )
//            uiState.invitadoRelevo.let { invitado ->
//                Text(
//                    text = "${invitado.empleo} ${invitado.nombre}",
//                    modifier = Modifier.padding(horizontal = 15.dp),
//                    style = Typography.titleMedium,
//                    textAlign = TextAlign.Center
//                )
//
//                AsyncImage(
//                    model = invitado.foto,
//                    contentDescription = null,
//                    contentScale = ContentScale.Fit,
//                    modifier = Modifier
//                        .sizeIn(
//                            maxWidth = 300.dp,
//                            maxHeight = 300.dp,
//                            minWidth = 200.dp,
//                            minHeight = 200.dp
//                        )
//                        .padding(7.dp)
//                )
//                val textConcierto = if (invitado.concierto == "1") {
//                    "Accede al concierto"
//                } else {
//                    "No accede al concierto"
//                }
//
//                Text(
//                    text = "${invitado.concierto} ${invitado.patio} ${invitado.jardines} ${invitado.vino}",
//                    modifier = Modifier.padding(
//                        start = 15.dp,
//                        end = 15.dp,
//                        top = 25.dp,
//                        bottom = 15.dp
//                    ),
//                    textAlign = TextAlign.Justify,
//                    color = Color.White
//                )
//                Text(
//                    text = "${invitado.concierto} ${invitado.patio} ${invitado.jardines} ${invitado.vino}",
//                    modifier = Modifier.padding(
//                        start = 15.dp,
//                        end = 15.dp,
//                        top = 25.dp,
//                        bottom = 15.dp
//                    ),
//                    textAlign = TextAlign.Justify,
//                    color = Color.White
//                )
//                Text(
//                    text = "${invitado.concierto} ${invitado.patio} ${invitado.jardines} ${invitado.vino}",
//                    modifier = Modifier.padding(
//                        start = 15.dp,
//                        end = 15.dp,
//                        top = 25.dp,
//                        bottom = 15.dp
//                    ),
//                    textAlign = TextAlign.Justify,
//                    color = Color.White
//                )
//                MyButton(text = "Actualizar acceso invitado") {
////                    eventsViewModel.showDialog(true)
//                }
            }
        }
    }
}
