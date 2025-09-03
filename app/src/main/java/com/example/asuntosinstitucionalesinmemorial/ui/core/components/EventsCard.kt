package com.example.asuntosinstitucionalesinmemorial.ui.core.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.asuntosinstitucionalesinmemorial.R

@Composable
fun EventsCard(
    name: String,
    date: String,
    photoUrl: String,
    onClick: (String) -> Unit,
    onLongClick: (String) -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp, 5.dp)
            .combinedClickable(
                onClick = { onClick(name) },
                onLongClick = { onLongClick(name) }
            ),
//            .clickable { onItemClick(name) },
        colors = CardDefaults.elevatedCardColors(
            containerColor = colorResource(R.color.onPrimary),
            contentColor = colorResource(R.color.white)
        ),
        shape = RoundedCornerShape(7.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            if (photoUrl.isNotEmpty()) {
                AsyncImage(
                    model = photoUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(90.dp)
                        .padding(7.dp)
                        .weight(1f)
                )
            } else {
                Image(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,
                    modifier = Modifier
                        .size(90.dp)
                        .padding(7.dp)
                        .weight(1f)
                )
            }
            Column(
                modifier = Modifier.weight(3f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = name,
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = date,
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp),
                    color = colorResource(R.color.white_transparent),
                    textAlign = TextAlign.Center
                )
            }
            Image(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                colorFilter = ColorFilter.tint(colorResource(R.color.white_transparent)),
                modifier = Modifier
                    .size(90.dp)
                    .padding(7.dp)
                    .weight(1f)
            )
        }
    }
}