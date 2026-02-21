package com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.guests

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
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
    isRelevo: Boolean,
    hasQrScanned: Boolean,
    navigateBack: () -> Unit
) {
    val uiState by guestDetailViewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        guestDetailViewModel.getGuestFirestore(
            event = event,
            guest = guest,
            isRelevo = isRelevo,
            hasQrScanned = hasQrScanned
        )
    }

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
//                LaunchedEffect(Unit) {
//                    if (hasQrScanned) {
//                        guestDetailViewModel.setGuestAccess(
//                            event = event,
//                            access = true,
//                            isRelevo = isRelevo
//                        )
//                    }
//                }

                if (uiState.isDialogShown) {
                    CreateDialog(
                        guestName = if (isRelevo) uiState.invitadoRelevo.nombre else uiState.invitado.nombre,
                        confirmAction = {
                            guestDetailViewModel.setGuestAccess(
                                event = event,
                                access = true,
                                isRelevo = isRelevo
                            )
                            guestDetailViewModel.showDialog(false)
                        },
                        denyAction = {
                            guestDetailViewModel.setGuestAccess(
                                event = event,
                                access = false,
                                isRelevo = isRelevo
                            )
                            guestDetailViewModel.showDialog(false)
                        },
                        dismissAction = {
                            guestDetailViewModel.showDialog(false)
                        }
                    )
                }

                val textModifier = Modifier.padding(horizontal = 15.dp)
                val generalGuest = uiState.invitado
                val relevoGuest = uiState.invitadoRelevo

                if (isRelevo) {
                    CreateGuestTexts(
                        name = relevoGuest.nombre,
                        photo = relevoGuest.foto,
                        visit = when {
                            relevoGuest.visita1.isNotEmpty() -> stringResource(R.string.first_visit)
                            relevoGuest.visita2.isNotEmpty() -> stringResource(R.string.second_visit)
                            else -> stringResource(R.string.no_visit)
                        },
                        actAccommodation = if (relevoGuest.patio.isNotEmpty()) stringResource(R.string.yard_relevo) else stringResource(
                            R.string.garden_relevo
                        ),
                        banquet = if (relevoGuest.vino.isNotEmpty()) stringResource(R.string.agape_access) else stringResource(
                            R.string.agape_deny
                        ),
                        color = relevoGuest.color,
                        vehicle = relevoGuest.vehiculo,
                        accesed = relevoGuest.accedido,
                        textModifier = textModifier
                    )
                } else {
                    CreateGuestTexts(
                        name = generalGuest.nombre,
                        photo = generalGuest.foto,
                        group = generalGuest.grupo,
                        position = generalGuest.cargo,
                        color = generalGuest.color,
                        vehicle = generalGuest.vehiculo,
                        accesed = generalGuest.accedido,
                        textModifier = textModifier
                    )
                }

                MyButton(text = stringResource(R.string.guest_access_btn)) {
                    guestDetailViewModel.showDialog(true)
                }
                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(
                        onClick = {
                            guestDetailViewModel.setGuestAccess(
                                event = event,
                                access = null,
                                isRelevo = isRelevo
                            )
                        },
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color(0xFFEA580C)
                        ),
                        border = BorderStroke(1.5.dp, Color(0xFFEA580C).copy(alpha = 0.4f)),
                        modifier = Modifier
                            .padding(horizontal = 50.dp, vertical = 10.dp)
                            .align(Alignment.CenterEnd),
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.Delete, contentDescription = null)
                            Text(stringResource(R.string.guest_attend_btn))
                        }
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

@Composable
private fun CreateGuestTexts(
    name: String = "",
    photo: String = "",
    visit: String = "",
    actAccommodation: String = "",
    banquet: String = "",
    color: String = "",
    group: String = "",
    position: String = "",
    vehicle: String = "",
    accesed: Boolean?,
    textModifier: Modifier
) {
    Text(
        text = name,
        modifier = textModifier,
        style = Typography.titleMedium,
        textAlign = TextAlign.Center
    )

    if (photo.isNotEmpty()) {
        AsyncImage(
            model = photo,
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
    } else {
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "Material",
            modifier = Modifier
                .size(200.dp)
                .padding(7.dp)
        )
    }

    if (visit.isNotEmpty()) {
        Text(
            text = visit,
            modifier = textModifier,
            style = Typography.titleMedium,
            textAlign = TextAlign.Center
        )
    }

    if (actAccommodation.isNotEmpty()) {
        Text(
            text = "Acomodación en el acto: $actAccommodation",
            modifier = textModifier,
            style = Typography.titleMedium,
            textAlign = TextAlign.Center
        )
    }

    if (banquet.isNotEmpty()) {
        Text(
            text = banquet,
            modifier = textModifier,
            style = Typography.titleMedium,
            textAlign = TextAlign.Center
        )
    }

    if (group.isNotEmpty()) {
        Text(
            text = group,
            modifier = textModifier,
            style = Typography.titleMedium,
            textAlign = TextAlign.Center
        )
    }

    if (position.isNotEmpty()) {
        Text(
            text = position,
            modifier = textModifier,
            style = Typography.titleMedium,
            textAlign = TextAlign.Center
        )
    }

    if (vehicle.isNotEmpty()) {
        Text(
            text = "Vehículo: $vehicle",
            modifier = textModifier,
            style = Typography.titleMedium,
            textAlign = TextAlign.Center
        )
    }

    if (color.isNotEmpty()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Color de la identificación:",
                modifier = textModifier,
                style = Typography.titleMedium,
                textAlign = TextAlign.Center
            )
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(Color(color.toColorInt()))
                    .clip(RoundedCornerShape(4.dp))
            )
        }
    }

    Text(
        text = when (accesed) {
            true -> stringResource(R.string.guest_accessed)
            false -> stringResource(R.string.guest_not_accessed)
            null -> stringResource(R.string.guest_not_attend)
        },
        modifier = textModifier,
        style = Typography.titleMedium,
        textAlign = TextAlign.Center,
        color = when (accesed) {
            true -> colorResource(R.color.onSuccess)
            else -> colorResource(R.color.onError)
        }
    )
}