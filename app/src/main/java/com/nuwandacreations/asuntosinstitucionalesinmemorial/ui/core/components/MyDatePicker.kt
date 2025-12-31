package com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.core.components

import android.icu.util.Calendar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.nuwandacreations.asuntosinstitucionalesinmemorial.util.Constants.Companion.CONFIRM

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePicker(
    datePickerState: DatePickerState,
    onDismiss: () -> Unit,
    onConfirm: (Long) -> Unit
) {
    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(onClick = {
                onConfirm(
                    datePickerState.selectedDateMillis ?: Calendar.getInstance().timeInMillis
                )
            }) {
                Text(CONFIRM)
            }
        },
    ) {
        DatePicker(datePickerState)
    }
}