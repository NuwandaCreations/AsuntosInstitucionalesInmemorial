package com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.model

import android.os.Parcelable
import com.nuwandacreations.asuntosinstitucionalesinmemorial.data.network.response.events.EventsResponse
import com.nuwandacreations.asuntosinstitucionalesinmemorial.util.dateToTimestamp
import kotlinx.parcelize.Parcelize

@Parcelize
data class Evento(
    var id: String? = null,
    var nombre: String? = null,
    var descripcion: String? = null,
    var fecha: String? = null,
    var lugar: String? = null,
    var imagen: String? = null,
    var esRelevoGuardia: Boolean = false
) : Parcelable

fun Evento.toData(): EventsResponse {
    return EventsResponse(
        id = id,
        nombre = nombre,
        descripcion = descripcion,
        fecha = dateToTimestamp(fecha),
        lugar = lugar,
        imagen = imagen,
        esRelevoGuardia = esRelevoGuardia
    )
}
