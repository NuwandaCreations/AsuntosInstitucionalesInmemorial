package com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.model

import com.nuwandacreations.asuntosinstitucionalesinmemorial.data.database.storagedb.model.RegalosEntity
import com.nuwandacreations.asuntosinstitucionalesinmemorial.util.Constants.Companion.EMPTY_NAME

data class Regalos(
    var cantidad: Int? = null,
    val categoria: String? = null,
    val detalleLocalizacion: String? = null,
    val localizacion: String? = null,
    val objeto: String? = null,
    val requisitoRegalo: String? = null,
    val foto: String = ""
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