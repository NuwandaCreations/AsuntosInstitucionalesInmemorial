package com.example.asuntosinstitucionalesinmemorial.ui.materialstorage

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.asuntosinstitucionalesinmemorial.R
import com.example.asuntosinstitucionalesinmemorial.ui.core.components.Card
import com.example.asuntosinstitucionalesinmemorial.ui.theme.Typography
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MaterialStorageScreen(
    materialStorageViewModel: MaterialStorageViewModel = koinViewModel(),
    goToDetail: (String) -> Unit
) {
    val uiState by materialStorageViewModel.uiState.collectAsStateWithLifecycle()
    var search by rememberSaveable { mutableStateOf("") }

    materialStorageViewModel.getMaterialFirestore()

    Scaffold(containerColor = colorResource(R.color.onPrimaryBackground)) { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Inventario material protocolo",
                modifier = Modifier.padding(top = 10.dp),
                style = Typography.titleMedium
            )
            OutlinedTextField(
                value = search,
                leadingIcon = {
                    Image(
                        Icons.Default.Menu,
                        contentDescription = "Menu",
                        modifier = Modifier
                            .size(20.dp)
                            .clickable {
                                //TODO DROPDOWN MENU
                            }
                    )
                },
                trailingIcon = {
                    Image(
                        Icons.Default.Search,
                        contentDescription = "Search",
                        modifier = Modifier
                            .size(20.dp)
                            .clickable {
                                //TODO SEARCH
                            }
                    )
                },
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 15.dp, start = 15.dp, end = 15.dp)
                    .fillMaxWidth(),
                onValueChange = { search = it },
                maxLines = 1,
                shape = RoundedCornerShape(40.dp),
                placeholder = { Text("Nombre del material...") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedLeadingIconColor = colorResource(R.color.onPrimary),
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                    focusedTextColor = Color.Black
                )
            )
            LazyColumn(
                flingBehavior = ScrollableDefaults.flingBehavior(),
                state = rememberLazyListState(),
            ) {
                items(uiState.storage.material) { material ->
                    Card("${material.objeto}", "${material.categoria}", R.drawable.ic_present) {
                        goToDetail(material.objeto.toString())
                    }
                }
            }

//            uiState.storage.regalos.forEach {
//                item {
//                    Card("${it.objeto}","${it.categoria}",R.drawable.ic_present)
//                }
//            }
        }
//        Column(modifier = Modifier.padding(padding).background(colorResource(R.color.onPrimaryBackground))) {
//            Card("Alfiler", "Coronelía", R.drawable.ic_present)
//            Card("Alfiler", "Coronelía", R.drawable.ic_present)
//            Card("Alfiler", "Coronelía", R.drawable.ic_present)
//            Card("Alfiler", "Coronelía", R.drawable.ic_present)
//            Card("Alfiler", "Coronelía", R.drawable.ic_present)
//            Card("Alfiler", "Coronelía", R.drawable.ic_present)
//        }
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