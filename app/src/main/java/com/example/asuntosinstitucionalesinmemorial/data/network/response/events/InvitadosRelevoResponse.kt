package com.example.asuntosinstitucionalesinmemorial.data.network.response.events

import com.example.asuntosinstitucionalesinmemorial.domain.model.InvitadosRelevo
import com.google.gson.annotations.SerializedName

data class InvitadosRelevoResponse(
    @SerializedName("EMPLEO") val empleo: String,
    @SerializedName("TRATAMIENTO") val tratamiento: String,
    @SerializedName("APELLIDOS") val apellidos: String,
    @SerializedName("NOMBRE") val nombre: String,
    @SerializedName("VEHÍCULO (MARCA, MODELO, COLOR Y MATRÍCULA)") val vehiculo: String,
    @SerializedName("OBSERVACIONES") val observaciones: String,
    @SerializedName("1ª VISITA") val visita1: String,
    @SerializedName("2ª VISITA") val visita2: String,
    @SerializedName("CONC") val concierto: String,
    @SerializedName("RELEVO") val patio: String,
    @SerializedName("JARDINES") val jardines: String,
    @SerializedName("VINO") val vino: String,
    @SerializedName("NOTA PROTOCOLO") val notaProtocolo: String,
)

fun InvitadosRelevoResponse.toDomain(): InvitadosRelevo {
    return InvitadosRelevo(
        empleo = empleo,
        tratamiento = tratamiento,
        nombre = "$nombre $apellidos",
        vehiculo = vehiculo,
        observaciones = observaciones,
        visita1 = visita1,
        visita2 = visita2,
        concierto = concierto,
        patio = patio,
        jardines = jardines,
        vino = vino,
        notaProtocolo = notaProtocolo,
        accedido = false
    )
}
