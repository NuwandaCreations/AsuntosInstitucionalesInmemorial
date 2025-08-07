package com.example.asuntosinstitucionalesinmemorial.domain.model

import com.example.asuntosinstitucionalesinmemorial.data.database.storagedb.model.RegalosEntity
import com.example.asuntosinstitucionalesinmemorial.util.Constants.Companion.EMPTY_NAME

data class Regalos(
    val cantidad: Int? = null,
    val categoria: String? = null,
    val detalleLocalizacion: String? = null,
    val localizacion: String? = null,
    val objeto: String? = null,
    val requisitoRegalo: String? = null
)

fun Regalos.toData(): RegalosEntity {
    return RegalosEntity(
        objeto ?: EMPTY_NAME,
        cantidad,
        categoria,
        detalleLocalizacion,
        localizacion,
        requisitoRegalo,
        null
    )
}
