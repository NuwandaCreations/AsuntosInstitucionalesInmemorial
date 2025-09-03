package com.example.asuntosinstitucionalesinmemorial.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.asuntosinstitucionalesinmemorial.R
import com.example.asuntosinstitucionalesinmemorial.ui.core.components.BasicDialog
import com.example.asuntosinstitucionalesinmemorial.ui.core.components.MyButton
import com.example.asuntosinstitucionalesinmemorial.ui.core.components.MySnackbar
import com.example.asuntosinstitucionalesinmemorial.ui.core.components.ProgressIndicator
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = koinViewModel()
) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }
    val scrollState = rememberScrollState()
    var buttonAction: ButtonAction = ButtonAction.REGALOS

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState) {
                MySnackbar(
                    text = stringResource(uiState.snackbarText ?: R.string.error),
                    color = uiState.snackbarColor ?: R.color.purple_500
                )
            }
        },
        containerColor = colorResource(R.color.onPrimaryBackground)
    ) { padding ->
        if (uiState.isDialogShown) {
            CreateDialog(
                buttonAction,
                confirmAction = {
                    homeViewModel.apply {
                        when (buttonAction) {
                            ButtonAction.REGALOS -> {
                                getRegalosStorage()
                            }

                            ButtonAction.MATERIAL -> {
                                getMaterialStorage()
                            }
                        }
                        showDialog(false)
                    }
                },
                dismissAction = {
                    homeViewModel.showDialog(false)
                }
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FrontPage()
            MyButton(stringResource(R.string.regalos_button)) {
                buttonAction = ButtonAction.REGALOS
                homeViewModel.showDialog(true)
            }
            MyButton(stringResource(R.string.material_button)) {
                buttonAction = ButtonAction.MATERIAL
                homeViewModel.showDialog(true)
            }
        }

        if (uiState.isLoading) {
            ProgressIndicator()
        }

        if (uiState.snackbarText != null) {
            homeViewModel.showSnackBar(snackBarHostState, stringResource(uiState.snackbarText!!))
        }
    }
}

@Composable
fun CreateDialog(buttonAction: ButtonAction, confirmAction: () -> Unit, dismissAction: () -> Unit) {
    var title: Int?
    var text: Int?
    when (buttonAction) {
        ButtonAction.REGALOS -> {
            title = R.string.regalos_dialog_title
            text = R.string.regalos_dialog_text
        }

        ButtonAction.MATERIAL -> {
            title = R.string.material_dialog_title
            text = R.string.material_dialog_text
        }
    }
    BasicDialog(
        title = stringResource(title),
        text = stringResource(text),
        confirmButton = { confirmAction() },
        dismissButton = { dismissAction() }
    )
}

@Composable
fun FrontPage() {
    Text(
        text = stringResource(R.string.motto_first),
        textAlign = TextAlign.Center,
        fontSize = 25.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily(Font(R.font.inknut_antiqua_semibold)),
        color = colorResource(R.color.white),
        modifier = Modifier.padding(bottom = 10.dp)
    )
    Image(
        painter = painterResource(R.drawable.ic_rinf1),
        contentDescription = "rinf1",
        Modifier
            .size(400.dp)
            .padding(20.dp)
    )
    Text(
        text = stringResource(R.string.motto_second),
        textAlign = TextAlign.Center,
        fontSize = 25.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily(Font(R.font.inknut_antiqua_semibold)),
        color = colorResource(R.color.white),
        modifier = Modifier.padding(top = 10.dp, bottom = 20.dp)
    )
}