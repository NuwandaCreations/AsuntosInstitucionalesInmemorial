package com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.guests

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.nuwandacreations.asuntosinstitucionalesinmemorial.R
import com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.core.components.MyButton
import com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.theme.Typography
import com.nuwandacreations.asuntosinstitucionalesinmemorial.util.Constants.Companion.ERROR_VALIDATING_GUEST
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuestDetailScreen(
    guestDetailViewModel: GuestDetailViewModel = koinViewModel(),
    event: String,
    guest: String,
    esRelevo: Boolean,
    navigateBack: () -> Unit
) {
    val uiState by guestDetailViewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    guestDetailViewModel.getGuestFirestore(event, guest, esRelevo)

    if (!uiState.emptyQrScan) {
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
                        guestName = uiState.invitado.nombre,
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
                }
            }
        }
    } else {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        IconButton(onClick = { navigateBack() }) {
                            Icon(
                                Icons.Filled.KeyboardArrowLeft,
                                null,
                                modifier = Modifier.size(70.dp),
                                tint = colorResource(R.color.onPrimary)
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Black
                    )
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = ERROR_VALIDATING_GUEST,
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                    color = colorResource(R.color.onError),
                    modifier = Modifier
                        .background(Color.Yellow)
                        .padding(horizontal = 10.dp, vertical = 30.dp)
                )
            }
        }
    }
}