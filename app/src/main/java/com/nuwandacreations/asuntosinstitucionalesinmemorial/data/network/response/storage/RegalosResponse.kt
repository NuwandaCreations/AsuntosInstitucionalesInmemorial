package com.nuwandacreations.asuntosinstitucionalesinmemorial.data.network.response.storage

import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.model.Regalos
import com.google.gson.annotations.SerializedName

data class RegalosResponse(
    @SerializedName("CANTIDAD") val cantidad: Int,
    @SerializedName("CATEGORÍA") val categoria: String,
    @SerializedName("DETALLE LOCALIZACIÓN") val detalleLocalizacion: String,
    @SerializedName("LOCALIZACIÓN") val localizacion: String,
    @SerializedName("OBJETO") val objeto: String,
    @SerializedName("REQUISITO DE REGALO") val requisitoRegalo: String
)

fun RegalosResponse.toDomain(): Regalos {
    return Regalos(cantidad, categoria, detalleLocalizacion, localizacion, objeto, requisitoRegalo)
}