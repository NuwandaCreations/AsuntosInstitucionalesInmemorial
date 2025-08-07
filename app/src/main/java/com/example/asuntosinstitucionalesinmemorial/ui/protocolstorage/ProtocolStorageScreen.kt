package com.example.asuntosinstitucionalesinmemorial.ui.protocolstorage

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.asuntosinstitucionalesinmemorial.domain.model.Material
import com.example.asuntosinstitucionalesinmemorial.domain.model.Regalos
import com.example.asuntosinstitucionalesinmemorial.ui.core.FirebaseImage
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProtocolStorageScreen(
    protocolStorageViewModel: ProtocolStorageViewModel = koinViewModel(),
    navigateBack: () -> Unit
) {
    val uiState by protocolStorageViewModel.uiState.collectAsStateWithLifecycle()
    val firestore = Firebase.firestore
    val firebaseStorage = Firebase.storage

//    protocolStorageViewModel.downloadStorage()
    val abanicos = Regalos(
       10,
        "Protocolo antigüo",
        "CAJA FUERTE",
        "DESPACHO CAP",
        "Abanicos",
        null
    )
    val material = Material(
        1,
        "FURRI",
        "CARTEL RINF 1",
        null
    )

    protocolStorageViewModel.setRegaloFirestore(abanicos)
//    protocolStorageViewModel.deleteRegalosFirestore(abanicos)
    protocolStorageViewModel.getRegalosFirestore()




//    protocolStorageViewModel.addRegalosToDB(*uiState.storage.regalos.toTypedArray())
//    firestore.collection("regalos").get().addOnSuccessListener { snapshot ->
//        snapshot.forEach { document ->
//            Log.i(document.id, "${document.data}")
//        }
//    }

        Scaffold { padding ->
            Column {
//            protocolStorageViewModel.getRegalosFromDB()
//            protocolStorageViewModel.getRegalosFromDB()
                protocolStorageViewModel.CircularProgressCountdown()
//            uiState.storage.regalos.forEach { regalo ->
//                FirebaseImage(
//                    storagePath = regalo.objeto.toString() + ".jpg", modifier = Modifier
//                        .clip(RoundedCornerShape(8.dp))
//                        .size(100.dp),
//                    progressVisibility = uiState.progressVisibility,
//                )
//            }


                Button(onClick = { navigateBack() }, modifier = Modifier.padding(padding)) {
                    Text(text = "Navigate Back")
                }
                val buttonText = if (!uiState.internetConnection) {
                    "There are not connection"
                } else {
                    uiState.storage.regalos.toString()
                }
                Text(
                    text = buttonText,
                    modifier = Modifier.clickable {
//                    protocolStorageViewModel.addMaterialToDB(*uiState.storage.material.toTypedArray())
//                    protocolStorageViewModel.addRegalosToDB(*uiState.storage.regalos.toTypedArray())

//                    protocolStorageViewModel.deleteMaterialFromDB(
//                        Material(
//                            32,
//                            "más",
//                            "más",
//                            "más"
//                        )
//                    )
                        protocolStorageViewModel.getMaterialFromDB()
//                    uiState.storage.material.forEach { material ->
//                        firestore.collection("material").document("${material.objeto}").set(material)
//                    }
                    }
                )
            }
        }
}