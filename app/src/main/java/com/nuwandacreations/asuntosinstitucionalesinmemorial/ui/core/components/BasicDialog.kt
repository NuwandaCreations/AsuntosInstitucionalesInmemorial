package com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.core.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.nuwandacreations.asuntosinstitucionalesinmemorial.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicDialog(title: String, text: String, confirmButton: () -> Unit, dismissButton: () -> Unit) {
    AlertDialog(
        onDismissRequest = { dismissButton() },
        confirmButton = {
            TextButton(onClick = { confirmButton() }) {
                Text("Aceptar", color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = { dismissButton() }) {
                Text("Cancelar", color = Color.White)
            }
        },
        title = {
            Text(text = title)
        },
        text = {
            Text(text = text)
        },
        containerColor = colorResource(R.color.onPrimary),
        titleContentColor = colorResource(R.color.white),
        textContentColor = colorResource(R.color.white)
    )
}
