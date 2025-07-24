package com.example.asuntosinstitucionalesinmemorial.view.core.network.response

import com.google.gson.annotations.SerializedName

data class Regalos(
    @SerializedName("CANTIDAD") val cantidad: Int,
    @SerializedName("CATEGORÍA") val categoria: String,
    @SerializedName("DETALLE LOCALIZACIÓN") val detalleLocalizacion: String,
    @SerializedName("LOCALIZACIÓN") val localizacion: String,
    @SerializedName("OBJETO") val objeto: String,
    @SerializedName("REQUISITO DE REGALO") val requisitoRegalo: String
)