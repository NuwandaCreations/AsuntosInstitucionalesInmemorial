package com.example.asuntosinstitucionalesinmemorial.ui.core

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.example.asuntosinstitucionalesinmemorial.R
import com.google.firebase.Firebase
import com.google.firebase.storage.storage

@Composable
fun FirebaseImage(
    storagePath: String,
    modifier: Modifier = Modifier,
    progressVisibility: Boolean = true
) {
    var imageUrl by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(storagePath) {
        val storageRef = Firebase.storage.reference.child(storagePath)
        storageRef.downloadUrl
            .addOnSuccessListener { uri ->
                imageUrl = uri.toString()
            }
            .addOnFailureListener {
                Log.e("FirebaseImage", "Error al obtener URL de la imagen", it)
            }
    }

    if (imageUrl != null) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = modifier,
            contentScale = ContentScale.Fit
        )
    } else {
        Box(
            modifier = modifier.background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            if (progressVisibility) {
                CircularProgressIndicator()
            } else {
                Image(
                    painter = painterResource(id = R.drawable.ic_no_photo),
                    contentDescription = "no photo found",
                    modifier = modifier,
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}