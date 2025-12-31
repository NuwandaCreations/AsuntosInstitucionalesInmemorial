package com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.events

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.firebase.Timestamp
import com.nuwandacreations.asuntosinstitucionalesinmemorial.R
import com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.core.components.MyButton
import com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.core.components.MyDatePicker
import com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.core.components.MyOutlinedTextField
import com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.core.components.MyRadioButton
import com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.core.components.MySnackbar
import com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.core.components.MySpacer
import com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.core.components.MyProgressIndicator
import com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.theme.Typography
import com.nuwandacreations.asuntosinstitucionalesinmemorial.util.Constants.Companion.AFIRMATIVE
import com.nuwandacreations.asuntosinstitucionalesinmemorial.util.Constants.Companion.EVENT_BUTTON
import com.nuwandacreations.asuntosinstitucionalesinmemorial.util.Constants.Companion.EVENT_DATE
import com.nuwandacreations.asuntosinstitucionalesinmemorial.util.Constants.Companion.EVENT_ID
import com.nuwandacreations.asuntosinstitucionalesinmemorial.util.Constants.Companion.EVENT_IS_RELEVO_GUARDIA
import com.nuwandacreations.asuntosinstitucionalesinmemorial.util.Constants.Companion.EVENT_NAME
import com.nuwandacreations.asuntosinstitucionalesinmemorial.util.Constants.Companion.EVENT_PLACE
import com.nuwandacreations.asuntosinstitucionalesinmemorial.util.Constants.Companion.EVENT_SCREEN_TITTLE
import com.nuwandacreations.asuntosinstitucionalesinmemorial.util.Constants.Companion.NEGATIVE
import com.nuwandacreations.asuntosinstitucionalesinmemorial.util.timestampToDate
import org.koin.compose.viewmodel.koinViewModel
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEventScreen(
    createEventViewModel: CreateEventViewModel = koinViewModel(),
    navigateBack: () -> Unit
) {
    val uiState by createEventViewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    val datePickerState = if (uiState.isInputMode) rememberDatePickerState(
        initialDisplayMode = DisplayMode.Input,
    ) else rememberDatePickerState(
        initialDisplayMode = DisplayMode.Picker,
    )

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
            Text(
                text = EVENT_SCREEN_TITTLE,
                modifier = Modifier.padding(horizontal = 15.dp),
                style = Typography.titleMedium,
                textAlign = TextAlign.Center
            )
            if (uiState.isDatePickerShown) {
                MyDatePicker(
                    datePickerState,
                    onDismiss = { createEventViewModel.showDatePicker(false) },
                    onConfirm = {
                        val dateEvent = timestampToDate(Timestamp(Date(it)))
                        createEventViewModel.updateEventState(date = dateEvent)
                        createEventViewModel.showDatePicker(false)
                    }
                )
            }
            MySpacer(height = 30)
            MyOutlinedTextField(
                uiState.event?.nombre ?: "",
                { createEventViewModel.updateEventState(name = it) },
                EVENT_NAME
            )
            MySpacer(height = 20)
            MyOutlinedTextField(
                uiState.event?.id ?: "",
                { createEventViewModel.updateEventState(id = it) },
                EVENT_ID
            )
            MySpacer(height = 20)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 50.dp)
            ) {
                OutlinedTextField(
                    value = uiState.event?.fecha ?: "",
                    onValueChange = { },
                    readOnly = true,
                    enabled = false,
                    label = { Text(EVENT_DATE) },
                    modifier = Modifier.clickable {
                        createEventViewModel.apply {
                            inputMode(true)
                            showDatePicker(true)
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedLabelColor = Color.White,
                        unfocusedLabelColor = Color.White,
                        disabledTextColor = Color.White,
                        disabledBorderColor = Color.White,
                        disabledLabelColor = Color.White
                    )
                )
                IconButton(onClick = {
                    createEventViewModel.apply {
                        inputMode(false)
                        showDatePicker(true)
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Open calendar",
                        modifier = Modifier.size(40.dp),
                        tint = Color.White
                    )
                }
            }
            MySpacer(height = 20)
            MyOutlinedTextField(
                uiState.event?.lugar ?: "",
                { createEventViewModel.updateEventState(place = it) },
                EVENT_PLACE
            )
            MySpacer(height = 20)
            Text(
                text = EVENT_IS_RELEVO_GUARDIA,
                modifier = Modifier.padding(horizontal = 15.dp),
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Row {
                val esRelevo = uiState.event?.esRelevoGuardia ?: false
                MyRadioButton(AFIRMATIVE, esRelevo) {
                    createEventViewModel.updateEventState(isRelevo = true)
                }
                MyRadioButton(NEGATIVE, !esRelevo) {
                    createEventViewModel.updateEventState(isRelevo = false)
                }
            }
            MySpacer(height = 20)
            MyButton(text = EVENT_BUTTON) {
                uiState.event?.let {
                    createEventViewModel.setEventFirestore(
                        it,
                        onSucces = { navigateBack() },
                        onError = { createEventViewModel.showError(true) }
                    )
                }
            }
        }
        if (uiState.isLoading) {
            MyProgressIndicator()
        }
        if (uiState.isErrorShown) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 25.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                MySnackbar(
                    text = "Complete los campos para crear el evento",
                    color = R.color.onError
                )
            }
        }
    }
}