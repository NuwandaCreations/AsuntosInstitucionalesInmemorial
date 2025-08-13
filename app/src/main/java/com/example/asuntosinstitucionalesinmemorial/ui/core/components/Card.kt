package com.example.asuntosinstitucionalesinmemorial.ui.core.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.asuntosinstitucionalesinmemorial.R

@Composable
fun Card(material: String, category: String, photo: Int) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp, 5.dp)
            .border(
                width = 0.6.dp,
                color = colorResource(R.color.white),
                shape = RoundedCornerShape(7.dp) // Debe coincidir con el shape del card
            ),
        colors = CardDefaults.elevatedCardColors(
            containerColor = colorResource(R.color.onPrimary),
            contentColor = colorResource(R.color.white)
        ),
        shape = RoundedCornerShape(7.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(3f), verticalArrangement = Arrangement.Center) {
                Text(
                    text = material,
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp)
                )
                Text(
                    text = category,
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp)
                )
            }
            Image(
                painter = painterResource(photo),
                contentDescription = "Material",
                modifier = Modifier
                    .size(90.dp)
                    .padding(7.dp)
                    .weight(1f)
            )
        }
    }
}