package com.nuwandacreations.asuntosinstitucionalesinmemorial.data.network.response.events

import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.model.Evento
import com.google.firebase.Timestamp
import com.nuwandacreations.asuntosinstitucionalesinmemorial.util.timestampToDate
import java.text.SimpleDateFormat
import java.util.Locale

data class EventsResponse(
    val id: String? = null,
    val nombre: String? = null,
    val descripcion: String? = null,
    val fecha: Timestamp? = null,
    val lugar: String? = null,
    val imagen: String? = null,
    val esRelevoGuardia: Boolean = false
)

fun EventsResponse.toDomain(): Evento {
    return Evento(
        id = id,
        nombre = nombre,
        descripcion = descripcion,
        fecha = timestampToDate(fecha),
        lugar = lugar,
        imagen = imagen,
        esRelevoGuardia = esRelevoGuardia
    )
}