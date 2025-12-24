package com.example.asuntosinstitucionalesinmemorial.data.network.response.events

import com.example.asuntosinstitucionalesinmemorial.domain.model.Invitados
import com.google.gson.annotations.SerializedName

data class InvitadosResponse(
    @SerializedName("EMPRESA") val empresa: String = "",
    @SerializedName("ASISTENTE") val asistente: String = "",
    @SerializedName("CARGO") val cargo: String = "",
    @SerializedName("VEHICULO") val vehiculo: String = "",
    @SerializedName("COLOR") val color: String = ""
)

fun InvitadosResponse.toDomain(): Invitados {
    return Invitados(
        grupo = empresa,
        nombre = asistente,
        cargo = cargo,
        vehiculo = vehiculo,
        accedido = false,
        foto = "",
        color = color
    )
}