package com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.model

import com.nuwandacreations.asuntosinstitucionalesinmemorial.data.network.response.events.EventsResponse
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

data class Evento(
    val id: String? = null,
    val nombre: String? = null,
    val descripcion: String? = null,
    val fecha: String? = null,
    val lugar: String? = null,
    val imagen: String? = null,
    val esRelevoGuardia: Boolean? = null
)

fun Evento.toData(): EventsResponse {
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val date = sdf.parse(fecha ?: "")
    val timestamp = date?.let { Timestamp(it) }
    return EventsResponse(
        id = id,
        nombre = nombre,
        descripcion = descripcion,
        fecha = timestamp,
        lugar = lugar,
        imagen = imagen,
        esRelevoGuardia = esRelevoGuardia
    )
}
