package com.example.asuntosinstitucionalesinmemorial.data.network.response

import com.example.asuntosinstitucionalesinmemorial.domain.model.Material
import com.google.gson.annotations.SerializedName

data class MaterialResponse(
    @SerializedName("CANTIDAD") val cantidad: Int,
    @SerializedName("LOCALIZACIÓN") val localizacion: String,
    @SerializedName("OBJETO") val objeto: String,
    @SerializedName("OBSERVACIONES") val observaciones: String
)

fun MaterialResponse.toDomain(): Material {
    return Material(cantidad, localizacion, objeto, observaciones)
}