package com.example.asuntosinstitucionalesinmemorial.ui.regalosstorage

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.asuntosinstitucionalesinmemorial.R
import com.example.asuntosinstitucionalesinmemorial.ui.core.components.Card
import com.example.asuntosinstitucionalesinmemorial.ui.core.navigation.StorageDetail
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RegalosStorageScreen(
    regalosStorageViewModel: RegalosStorageViewModel = koinViewModel(),
    navController: NavController
) {
    val uiState by regalosStorageViewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(containerColor = colorResource(R.color.onPrimaryBackground)) { padding ->
        Column(modifier = Modifier.padding(padding).background(colorResource(R.color.onPrimaryBackground))) {
            Card("Alfiler", "Coronelía", R.drawable.ic_present)
            Card("Alfiler", "Coronelía", R.drawable.ic_present)
            Card("Alfiler", "Coronelía", R.drawable.ic_present)
            Card("Alfiler", "Coronelía", R.drawable.ic_present)
            Button(
                onClick = { navController.navigate(StorageDetail) },
                modifier = Modifier.padding(padding)
            ) {
                Text(text = "Navigate Back")
            }
        }
    }
//    protocolStorageViewModel.downloadStorage()
//    val abanicos = Regalos(
//       10,
//        "Protocolo antigüo",
//        "CAJA FUERTE",
//        "DESPACHO CAP",
//        "Abanicos",
//        null
//    )
//    val material = Material(
//        1,
//        "FURRI",
//        "CARTEL RINF 1",
//        null
//    )

//    protocolStorageViewModel.setRegaloFirestore(abanicos)
//    protocolStorageViewModel.deleteRegalosFirestore(abanicos)
//    protocolStorageViewModel.getRegalosFirestore()


//    protocolStorageViewModel.addRegalosToDB(*uiState.storage.regalos.toTypedArray())
//    firestore.collection("regalos").get().addOnSuccessListener { snapshot ->
//        snapshot.forEach { document ->
//            Log.i(document.id, "${document.data}")
//        }
//    }

//        Scaffold { padding ->
//            Column {
////            protocolStorageViewModel.getRegalosFromDB()
////            protocolStorageViewModel.getRegalosFromDB()
//                regalosStorageViewModel.CircularProgressCountdown()
////            uiState.storage.regalos.forEach { regalo ->
////                FirebaseImage(
////                    storagePath = regalo.objeto.toString() + ".jpg", modifier = Modifier
////                        .clip(RoundedCornerShape(8.dp))
////                        .size(100.dp),
////                    progressVisibility = uiState.progressVisibility,
////                )
////            }
//
//
//                Button(onClick = { navController.navigate(StorageDetail) }, modifier = Modifier.padding(padding)) {
//                    Text(text = "Navigate Back")
//                }
//                val buttonText = if (!uiState.internetConnection) {
//                    "There are not connection"
//                } else {
//                    uiState.storage.regalos.toString()
//                }
//                Text(
//                    text = buttonText,
//                    modifier = Modifier.clickable {
////                    protocolStorageViewModel.addMaterialToDB(*uiState.storage.material.toTypedArray())
////                    protocolStorageViewModel.addRegalosToDB(*uiState.storage.regalos.toTypedArray())
//
////                    protocolStorageViewModel.deleteMaterialFromDB(
////                        Material(
////                            32,
////                            "más",
////                            "más",
////                            "más"
////                        )
////                    )
////                        protocolStorageViewModel.getMaterialFromDB()
////                    uiState.storage.material.forEach { material ->
////                        firestore.collection("material").document("${material.objeto}").set(material)
////                    }
//                        regalosStorageViewModel.getRegalosStorage()
////                        protocolStorageViewModel.getMaterialStorage()
//                    }
//                )
//            }
//        }
}