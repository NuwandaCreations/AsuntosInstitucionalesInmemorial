package com.example.asuntosinstitucionalesinmemorial.ui.materialstorage

import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.asuntosinstitucionalesinmemorial.R
import com.example.asuntosinstitucionalesinmemorial.ui.core.components.Card
import com.example.asuntosinstitucionalesinmemorial.ui.core.components.MyTextField
import com.example.asuntosinstitucionalesinmemorial.ui.theme.Typography
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MaterialStorageScreen(
    materialStorageViewModel: MaterialStorageViewModel = koinViewModel(),
    goToDetail: (String) -> Unit
) {
    val uiState by materialStorageViewModel.uiState.collectAsStateWithLifecycle()
    var searchText by rememberSaveable { mutableStateOf("") }
    var expandedMenu by rememberSaveable { mutableStateOf(false) }
    var categoriaMenu by rememberSaveable { mutableStateOf("Todos") }

    materialStorageViewModel.getAllPhotos()
    materialStorageViewModel.getMaterialFirestore()

    Scaffold(
        containerColor = colorResource(R.color.onPrimaryBackground),
        floatingActionButton = {
            FloatingActionButton(onClick = {
//                goToEdit()
            }) {
                Icon(Icons.Default.Add, null)
            }
        },
        floatingActionButtonPosition = FabPosition.EndOverlay
    ) { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.material_screen_title),
                modifier = Modifier.padding(top = 10.dp),
                style = Typography.titleMedium
            )

            MyTextField(
                searchText = searchText,
                placeholderText = stringResource(R.string.placeholder_material),
                expandedMenu = expandedMenu,
                material = uiState.storage.material,
                onExpandedMenu = { expandedMenu = it },
                onSearchText = { searchText = it },
                onCategoriaMenu = { categoriaMenu = it }
            )

            LazyColumn(
                flingBehavior = ScrollableDefaults.flingBehavior(),
                state = rememberLazyListState(),
            ) {
                items(uiState.storage.material) { material ->
                    if (categoriaMenu == stringResource(R.string.all) || categoriaMenu == material.categoria.toString()) {
                        if (searchText.isEmpty() || material.objeto.toString()
                                .contains(searchText, ignoreCase = true)
                        ) {
                            if (material.foto.isEmpty()) {
                                materialStorageViewModel.getPhoto(material)
                            }
                            Card(
                                material = "${material.objeto}",
                                category = "${material.categoria}",
                                photoUrl = material.foto
                            ) {
                                goToDetail(material.objeto.toString())
                            }
                        }
                    }
                }
            }
        }
    }
}