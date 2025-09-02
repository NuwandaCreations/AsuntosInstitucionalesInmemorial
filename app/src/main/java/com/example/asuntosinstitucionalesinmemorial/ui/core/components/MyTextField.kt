package com.example.asuntosinstitucionalesinmemorial.ui.core.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.asuntosinstitucionalesinmemorial.R
import com.example.asuntosinstitucionalesinmemorial.domain.model.Material
import com.example.asuntosinstitucionalesinmemorial.domain.model.Regalos

@Composable
fun MyTextField(
    searchText: String,
    placeholderText: String,
    expandedMenu: Boolean,
    regalos: List<Regalos> = emptyList(),
    material: List<Material> = emptyList(),
    onExpandedMenu: (Boolean) -> Unit,
    onSearchText: (String) -> Unit,
    onCategoriaMenu: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = searchText,
            leadingIcon = {
                Image(
                    Icons.Default.Menu,
                    contentDescription = "Menu",
                    modifier = Modifier
                        .size(20.dp)
                        .clickable {
                            onExpandedMenu(true)
                        }
                )
            },
            trailingIcon = {
                Image(
                    Icons.Default.Search,
                    contentDescription = "Search",
                    modifier = Modifier
                        .size(20.dp)
                )
            },
            modifier = Modifier
                .padding(top = 10.dp, start = 15.dp, end = 15.dp)
                .fillMaxWidth(),
            onValueChange = { onSearchText(it) },
            maxLines = 1,
            shape = RoundedCornerShape(40.dp),
            placeholder = { Text(placeholderText) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedLeadingIconColor = colorResource(R.color.onPrimary),
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White,
                focusedTextColor = Color.Black
            )
        )

        Box(
            contentAlignment = Alignment.TopStart,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp)
        ) {
            DropdownMenu(expanded = expandedMenu, onDismissRequest = { onExpandedMenu(false) }) {
                val allCat = stringResource(R.string.all)
                DropdownMenuItem(text = { Text(allCat) }, onClick = {
                    onCategoriaMenu(allCat)
                    onExpandedMenu(false)
                })

                val categorias = if (regalos.isNotEmpty()) {
                    regalos.map { it.categoria }.distinct()
                } else if (material.isNotEmpty()) {
                    material.map { it.categoria }.distinct()
                } else {
                    null
                }
                categorias?.forEach { categoria ->
                    val selectedCat = categoria.toString()
                    DropdownMenuItem(text = { Text(selectedCat) }, onClick = {
                        onCategoriaMenu(selectedCat)
                        onExpandedMenu(false)
                    })
                }
            }
        }

        Spacer(Modifier.height(15.dp))
    }
}