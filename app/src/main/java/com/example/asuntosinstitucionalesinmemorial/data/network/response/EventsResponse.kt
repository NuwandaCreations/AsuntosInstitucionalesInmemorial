package com.example.asuntosinstitucionalesinmemorial.data.network.response

import com.example.asuntosinstitucionalesinmemorial.domain.model.Evento
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

data class EventsResponse(
    val id: String? = null,
    val nombre: String? = null,
    val descripcion: String? = null,
    val fecha: Timestamp? = null,
    val lugar: String? = null,
    val imagen: String? = null
)

fun EventsResponse.toDomain(): Evento {
    val date = fecha?.toDate()
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val formatedDate = sdf.format(date ?: "")
    return Evento(
        id = id,
        nombre = nombre,
        descripcion = descripcion,
        fecha = formatedDate,
        lugar = lugar,
        imagen = imagen
    )
}