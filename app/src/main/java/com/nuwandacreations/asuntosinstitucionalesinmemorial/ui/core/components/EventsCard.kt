package com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.core.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.nuwandacreations.asuntosinstitucionalesinmemorial.R

@Composable
fun EventsCard(
    name: String,
    date: String,
    place: String,
    photoUrl: String,
    onClick: (String) -> Unit,
    onLongClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .height(400.dp)
            .width(250.dp)
            .padding(start = 10.dp, end = 10.dp)
            .combinedClickable(
                onClick = { onClick(name) },
                onLongClick = { onLongClick(name) }
            )
            .border(1.dp, color = Color.White, RoundedCornerShape(7.dp)),
        colors = CardDefaults.elevatedCardColors(
            containerColor = colorResource(R.color.onPrimaryTransparent),
            contentColor = colorResource(R.color.white)
        ),
        shape = RoundedCornerShape(7.dp),
        border = BorderStroke(1.dp, Color.White),
        elevation = CardDefaults.cardElevation(6.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .height(700.dp)
        ) {
            Text(
                text = name,
                modifier = Modifier
                    .padding(start = 7.dp, end = 7.dp, top = 20.dp)
                    .weight(3f),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                maxLines = 2,
                style = TextStyle(
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.7f),
                        offset = Offset(2f, 2f),
                        blurRadius = 4f
                    )
                )
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(6f)
                    .padding(start = 7.dp, end = 7.dp, bottom = 7.dp),
            ) {
                if (photoUrl.isNotEmpty()) {
                    AsyncImage(
                        model = photoUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                } else {
                    Image(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
            }
            Text(
                text = place,
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 5.dp)
                    .weight(2f),
                color = colorResource(R.color.white_transparent),
                textAlign = TextAlign.Center
            )
            Text(
                text = date,
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 10.dp)
                    .weight(2f),
                color = colorResource(R.color.white_transparent),
                textAlign = TextAlign.Center
            )
        }
    }
}