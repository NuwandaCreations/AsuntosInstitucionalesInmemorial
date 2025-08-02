package com.example.asuntosinstitucionalesinmemorial.ui.protocolstorage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.asuntosinstitucionalesinmemorial.domain.model.Material
import com.example.asuntosinstitucionalesinmemorial.domain.model.Regalos
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProtocolStorageScreen(
    protocolStorageViewModel: ProtocolStorageViewModel = koinViewModel(),
    navigateBack: () -> Unit
) {
    val uiState by protocolStorageViewModel.uiState.collectAsStateWithLifecycle()

    val regalo = arrayOf(
        Regalos(
            3,
            "regalito",
            "regalito",
            "regalito",
            "regalito",
            "regalito"
        ),
        Regalos(
            7,
            "segundo",
            "segundo",
            "segundo",
            "segundo",
            "segundo"
        ),
        Regalos(
            12,
            "último",
            "último",
            "último",
            "último",
            "último"
        )
    )
    protocolStorageViewModel.addRegalosToDB(*regalo)

    val material = arrayOf(
        Material(
            2,
            "miMaterial",
            "miMaterial",
            "miMaterial"
        ),
        Material(
            17,
            "otro",
            "otro",
            "otro"
        ),
        Material(
            32,
            "más",
            "más",
            "más"
        )
    )
    protocolStorageViewModel.addMaterialToDB(*material)

    Scaffold { padding ->
        Column {
            Button(onClick = { navigateBack() }, modifier = Modifier.padding(padding)) {
                Text(text = "Navigate Back")
            }
            val buttonText = if (!uiState.internetConnection) {
                "There are not connection"
            } else {
                if (uiState.storage.material.isNotEmpty()) {
                    uiState.storage.material.toString()
                } else {
                    "material is empty"
                }
            }
            Text(
                text = buttonText,
                modifier = Modifier.clickable {
//                    protocolStorageViewModel.deleteRegalosFromDB(
//                        *arrayOf(
//                            Regalos(
//                                3,
//                                "regalito",
//                                "regalito",
//                                "regalito",
//                                "regalito",
//                                "regalito"
//                            ),
//                            Regalos(
//                                7,
//                                "segundo",
//                                "segundo",
//                                "segundo",
//                                "segundo",
//                                "segundo"
//                            )
//                        )
//                    )
//                    protocolStorageViewModel.getRegalosFromDB()
                    protocolStorageViewModel.deleteMaterialFromDB(
                        Material(
                            32,
                            "más",
                            "más",
                            "más"
                        )
                    )
                    protocolStorageViewModel.getMaterialFromDB()
                })
        }
    }
}