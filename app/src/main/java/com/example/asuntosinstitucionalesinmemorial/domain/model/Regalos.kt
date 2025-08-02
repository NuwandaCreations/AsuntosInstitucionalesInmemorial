package com.example.asuntosinstitucionalesinmemorial.domain.model

import com.example.asuntosinstitucionalesinmemorial.data.database.storagedb.model.RegalosEntity
import com.example.asuntosinstitucionalesinmemorial.util.Constants.Companion.EMPTY_NAME

data class Regalos(
    val cantidad: Int?,
    val categoria: String?,
    val detalleLocalizacion: String?,
    val localizacion: String?,
    val objeto: String?,
    val requisitoRegalo: String?
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
