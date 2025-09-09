package com.example.asuntosinstitucionalesinmemorial.ui.storagedetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.asuntosinstitucionalesinmemorial.R
import com.example.asuntosinstitucionalesinmemorial.ui.core.components.ProgressIndicator
import com.example.asuntosinstitucionalesinmemorial.ui.home.ButtonAction
import com.example.asuntosinstitucionalesinmemorial.ui.theme.Typography
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun StorageDetailScreen(
    detailViewModel: StorageDetailViewModel = koinViewModel(),
    objeto: String,
    type: ButtonAction,
    navigateBack: () -> Unit
) {
    val uiState by detailViewModel.uiState.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }
    val scrollState = rememberScrollState()

    detailViewModel.getPhoto(objeto)
    when (type) {
        ButtonAction.REGALOS -> {
            detailViewModel.getRegaloByIdFirestore(objeto)
        }

        ButtonAction.MATERIAL -> {
            detailViewModel.getMaterialByIdFirestore(objeto)
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState) {
//                MySnackbar(
//                    text = stringResource(uiState.snackbarText ?: R.string.error),
//                    color = uiState.snackbarColor ?: R.color.purple_500
//                )
            }
        },
        containerColor = colorResource(R.color.onPrimaryBackground)
    ) { padding ->
        if (uiState.progressVisibility) {
            ProgressIndicator()
        }
        when (type) {
            ButtonAction.REGALOS -> {
                uiState.regalo?.let {
                    CreateView(
                        it.objeto.toString(),
                        it.cantidad.toString(),
                        it.categoria.toString(),
                        it.localizacion.toString(),
                        it.requisitoRegalo.toString(),
                        uiState.photoUrl ?: "",
                        scrollState,
                        plusNumber = {
                            detailViewModel.regaloPlusNumber(it)
                        },
                        minusNumber = {
                            detailViewModel.regaloMinusNumber(it)
                        })
                }
            }

            ButtonAction.MATERIAL -> {
                uiState.material?.let {
                    CreateView(
                        it.objeto.toString(),
                        it.cantidad.toString(),
                        it.categoria.toString(),
                        it.localizacion.toString(),
                        it.observaciones.toString(),
                        uiState.photoUrl ?: "",
                        scrollState,
                        plusNumber = {
                            detailViewModel.materialPlusNumber(it)
                        },
                        minusNumber = {
                            detailViewModel.materialMinusNumber(it)
                        })
                }
            }
        }
    }
}

@Composable
fun CreateView(
    objeto: String,
    cantidad: String,
    categoria: String,
    localizacion: String,
    observaciones: String,
    photoUrl: String,
    scrollState: ScrollState,
    plusNumber: () -> Unit,
    minusNumber: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Text(
            text = objeto,
            modifier = Modifier
                .padding(top = 10.dp, start = 5.dp, end = 5.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = Typography.titleMedium
        )
        if (photoUrl.isNotEmpty()) {
            AsyncImage(
                model = photoUrl,
                contentDescription = "photo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .clickable {
                        //TODO foto en pantalla completa ampliable
                    }
            )
        } else {
            Image(
                painter = painterResource(R.drawable.ic_rinf1),
                alignment = Alignment.Center,
                contentDescription = "rinf1",
                modifier = Modifier
                    .size(400.dp)
                    .padding(40.dp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Cantidad : $cantidad",
                style = Typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
            )
            IconButton(
                onClick = { plusNumber() },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = colorResource(R.color.onPrimary)
                ),
                modifier = Modifier.border(
                    width = 2.dp,
                    color = Color.White,
                    shape = RoundedCornerShape(200.dp)
                )
            ) {
                Text(
                    "+",
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            IconButton(
                onClick = { minusNumber() },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = colorResource(R.color.onPrimary)
                ),
                modifier = Modifier
                    .border(
                        width = 2.dp,
                        color = Color.White,
                        shape = RoundedCornerShape(200.dp)
                    )
            ) {
                Text(
                    "-",
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )

            }
        }
        PropertiesText("Categoría", categoria)
        PropertiesText("Localización", localizacion)
        PropertiesText("Observaciones", observaciones)
    }
}

@Composable
fun PropertiesText(propertie: String, value: String) {
    Text(
        text = "$propertie : $value",
        style = Typography.titleMedium,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .fillMaxWidth()
    )
}